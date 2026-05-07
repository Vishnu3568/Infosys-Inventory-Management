package com.inventory.database_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Category() {}

    public Category(Long id, String name, String description) {
        this.id          = id;
        this.name        = name;
        this.description = description;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long getId()          { return id; }
    public String getName()      { return name; }
    public String getDescription() { return description; }
    public Boolean getActive() { return active; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setId(Long id)               { this.id = id; }
    public void setName(String name)         { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setActive(Boolean active) { this.active = active; }
}
