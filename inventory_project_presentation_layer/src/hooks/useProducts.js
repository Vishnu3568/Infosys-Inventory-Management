import { useState, useEffect, useCallback } from 'react';
import { api, normalizeProducts, toProductPayload } from '../utilities/ApiUtils';

export function useProducts() {
  const [products, setProducts] = useState([]);
  const [loading,  setLoading]  = useState(false);
  const [error,    setError]    = useState(null);

  const load = useCallback(async () => {
    setLoading(true); setError(null);
    try {
      const data = await api.get('/products');
      setProducts(normalizeProducts(data));
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { load(); }, [load]);

  const addProduct = async (product) => {
    const created = await api.post('/product', toProductPayload(product));
    const normalized = normalizeProducts([created])[0];
    setProducts(prev => [normalized, ...prev]);
    return normalized;
  };

  const editProduct = async (id, data) => {
    const updated = await api.put(`/product/${id}`, toProductPayload(data));
    const normalized = normalizeProducts([updated])[0];
    setProducts(prev => prev.map(p => p.id === id ? normalized : p));
    return normalized;
  };

  const removeProduct = async (id) => {
    await api.delete(`/product/${id}`);
    setProducts(prev => prev.filter(p => p.id !== id));
  };

  return {
    products, loading, error,
    refresh: load,
    lowStockProducts: products.filter(p => p.quantity <= p.reorderLevel),
    addProduct, editProduct, removeProduct,
  };
}
