param(
  [switch]$SkipDatabaseSystem,
  [switch]$SkipAuth,
  [switch]$SkipBusiness,
  [switch]$SkipFrontend
)

function Import-DotEnv {
  param([string]$Path)

  if (-not (Test-Path $Path)) {
    return
  }

  Get-Content $Path | ForEach-Object {
    $line = $_.Trim()
    if (-not $line -or $line.StartsWith('#')) {
      return
    }

    $parts = $line.Split('=', 2)
    if ($parts.Count -ne 2) {
      return
    }

    $name = $parts[0].Trim()
    $value = $parts[1].Trim().Trim('"').Trim("'")
    if ($name) {
      [Environment]::SetEnvironmentVariable($name, $value, 'Process')
    }
  }
}

function Start-ServiceProcess {
  param(
    [string]$Name,
    [string]$WorkingDir,
    [string]$Command
  )

  Write-Host "Starting $Name..."
  Start-Process powershell.exe -WorkingDirectory $WorkingDir -ArgumentList @('-NoLogo', '-NoExit', '-Command', $Command)
}

function Stop-ProcessOnPort {
  param(
    [int]$Port,
    [string]$Reason
  )

  $listeners = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue
  if (-not $listeners) {
    return
  }

  $pids = $listeners | Select-Object -ExpandProperty OwningProcess -Unique
  foreach ($procId in $pids) {
    try {
      $proc = Get-Process -Id $procId -ErrorAction Stop
      Write-Host "Stopping process $($proc.ProcessName) (PID $procId) on port $Port ($Reason)..."
      Stop-Process -Id $procId -Force -ErrorAction Stop
    } catch {
      Write-Warning ("Failed to stop PID {0} on port {1}. {2}" -f $procId, $Port, $_.Exception.Message)
    }
  }
}

function Test-HttpEndpoint {
  param(
    [string]$Url
  )

  try {
    $response = Invoke-WebRequest -Uri $Url -Method Get -UseBasicParsing -TimeoutSec 3 -ErrorAction Stop
    return $response.StatusCode -ge 200 -and $response.StatusCode -lt 300
  } catch {
    return $false
  }
}

function Get-ListeningPid {
  param([int]$Port)

  $listener = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue |
    Select-Object -First 1

  if ($listener) {
    return $listener.OwningProcess
  }

  return $null
}

function Get-DotEnvValue {
  param(
    [string]$Path,
    [string]$Name,
    [string]$DefaultValue
  )

  if (-not (Test-Path $Path)) {
    return $DefaultValue
  }

  $line = Get-Content $Path | Where-Object {
    $trimmed = $_.Trim()
    $trimmed -and -not $trimmed.StartsWith('#') -and $trimmed -match "^$Name\s*="
  } | Select-Object -First 1

  if (-not $line) {
    return $DefaultValue
  }

  $parts = $line.Split('=', 2)
  if ($parts.Count -ne 2) {
    return $DefaultValue
  }

  return $parts[1].Trim().Trim('"').Trim("'")
}

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$dbEnvPath = Join-Path $root 'inventory-database-system\.env'
$authEnvPath = Join-Path $root 'inventory-authentication-module-main\.env'
$businessEnvPath = Join-Path $root 'IM-Business-Layer\.env'
$frontendEnvPath = Join-Path $root 'inventory_project_presentation_layer\.env.local'

if (-not $SkipDatabaseSystem) {
  $dbPort = Get-DotEnvValue -Path $dbEnvPath -Name 'SERVER_PORT' -DefaultValue '8083'
  Stop-ProcessOnPort -Port $dbPort -Reason 'clear target database port'
  $dbCommand = ("`$env:SERVER_PORT='{0}'; mvn spring-boot:run" -f $dbPort)
  Start-ServiceProcess -Name 'database-system' -WorkingDir (Join-Path $root 'inventory-database-system') -Command $dbCommand
}

if (-not $SkipAuth) {
  $authPortValue = Get-DotEnvValue -Path $authEnvPath -Name 'SERVER_PORT' -DefaultValue '8084'
  $authPort = [int]$authPortValue
  $authHealthUrl = "http://localhost:$authPort/api/v1/public/health"
  $authPid = Get-ListeningPid -Port $authPort

  if ($authPid -and (Test-HttpEndpoint -Url $authHealthUrl)) {
    Write-Host "Auth already running on port $authPort (PID $authPid). Skipping restart."
  } else {
    Stop-ProcessOnPort -Port 8081 -Reason 'clear legacy auth port before auth launch'
    Stop-ProcessOnPort -Port $authPort -Reason 'clear target auth port before auth launch'
    $authCommand = ("`$env:SERVER_PORT='{0}'; mvn spring-boot:run" -f $authPort)
    Start-ServiceProcess -Name 'auth' -WorkingDir (Join-Path $root 'inventory-authentication-module-main') -Command $authCommand
  }
}

if (-not $SkipBusiness) {
  $businessPortValue = Get-DotEnvValue -Path $businessEnvPath -Name 'SERVER_PORT' -DefaultValue '8082'
  $businessPort = [int]$businessPortValue
  $businessHealthUrl = "http://localhost:$businessPort/api/products"
  $businessPid = Get-ListeningPid -Port $businessPort

  if ($businessPid -and (Test-HttpEndpoint -Url $businessHealthUrl)) {
    Write-Host "Business already running on port $businessPort (PID $businessPid). Skipping restart."
  } else {
    Stop-ProcessOnPort -Port $businessPort -Reason 'clear stale business listener before launch'
    $businessCommand = ("`$env:SERVER_PORT='{0}'; mvn spring-boot:run" -f $businessPort)
    Start-ServiceProcess -Name 'business' -WorkingDir (Join-Path $root 'IM-Business-Layer') -Command $businessCommand
  }
}

if (-not $SkipFrontend) {
  Start-ServiceProcess -Name 'frontend' -WorkingDir (Join-Path $root 'inventory_project_presentation_layer') -Command 'npm start'
}

Write-Host 'Services launched. Close the spawned PowerShell windows to stop them.'
