import { api } from '../utilities/ApiUtils';
 
export const ProductService = {
  getAll:        ()          => api.get('/products'),
  getById:       (id)        => api.get(`/product/${id}`),
  search:        (q)         => api.get(`/products/search?name=${encodeURIComponent(q)}`),
  getByCategory: (cat)       => api.get(`/products/category/${encodeURIComponent(cat)}`),
  getLowStock:   ()          => api.get('/products/low-stock'),
  create:        (product)   => api.post('/product', product),
  update:        (id, data)  => api.put(`/product/${id}`, data),
  delete:        (id)        => api.delete(`/product/${id}`),
  restock:       (id, qty)   => api.post(`/increase/${id}/${qty}`),
  generateReport: (username, role) => api.get(`/report?username=${encodeURIComponent(username)}&role=${encodeURIComponent(role)}`),
};