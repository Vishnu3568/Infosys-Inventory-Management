import { API_BASE_URL, AUTH_API_BASE_URL, TOKEN_KEY } from './Constants';
 
const getToken = () => localStorage.getItem(TOKEN_KEY);
 
const headers = (extra = {}) => ({
  'Content-Type': 'application/json',
  ...(getToken() ? { Authorization: `Bearer ${getToken()}` } : {}),
  ...extra,
});
 
const handleResponse = async (res) => {
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.error || `HTTP ${res.status}`);
  return data;
};

const createApi = (baseUrl) => ({
  get: (path) =>
    fetch(`${baseUrl}${path}`, { headers: headers() }).then(handleResponse),

  post: (path, body) =>
    fetch(`${baseUrl}${path}`, {
      method: 'POST',
      headers: headers(),
      body: JSON.stringify(body),
    }).then(handleResponse),

  put: (path, body) =>
    fetch(`${baseUrl}${path}`, {
      method: 'PUT',
      headers: headers(),
      body: JSON.stringify(body),
    }).then(handleResponse),

  delete: (path) =>
    fetch(`${baseUrl}${path}`, {
      method: 'DELETE',
      headers: headers(),
    }).then(handleResponse),
});
 
export const api = {
  ...createApi(API_BASE_URL),
};

export const authApi = {
  ...createApi(AUTH_API_BASE_URL),
};

export const normalizeProduct = (product) => {
  if (!product) return product;

  const category = typeof product.category === 'string'
    ? product.category
    : product.category?.name || '';

  const supplier = typeof product.supplier === 'string'
    ? product.supplier
    : product.supplier?.name || '';

  return {
    ...product,
    unitPrice: product.unitPrice ?? Number(product.price ?? 0),
    category,
    supplier,
  };
};

export const normalizeProducts = (products = []) => products.map(normalizeProduct);

export const toProductPayload = (product) => ({
  name: product.name,
  description: product.description || null,
  sku: product.sku || null,
  price: Number(product.unitPrice ?? product.price ?? 0),
  quantity: Number(product.quantity ?? 0),
  reorderLevel: Number(product.reorderLevel ?? 0),
  category: product.category || null,
  supplier: product.supplier || null,
  active: product.active ?? true,
});