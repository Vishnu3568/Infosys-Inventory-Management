import { createContext, useContext, useState, useCallback } from 'react';
import { api, normalizeProducts, toProductPayload } from '../utilities/ApiUtils';

const InventoryContext = createContext(null);

export function InventoryProvider({ children }) {
  const [products, setProducts] = useState([]);
  const [loading,  setLoading]  = useState(false);
  const [error,    setError]    = useState(null);

  const fetchProducts = useCallback(async () => {
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

  const addProduct = async (product) => {
    const data = await api.post('/product', toProductPayload(product));
    const normalized = normalizeProducts([data])[0];
    setProducts(prev => [normalized, ...prev]);
    return normalized;
  };

  const updateProduct = async (id, product) => {
    const data = await api.put(`/product/${id}`, toProductPayload(product));
    const normalized = normalizeProducts([data])[0];
    setProducts(prev => prev.map(p => p.id === id ? normalized : p));
    return normalized;
  };

  const deleteProduct = async (id) => {
    await api.delete(`/product/${id}`);
    setProducts(prev => prev.filter(p => p.id !== id));
  };

  const getLowStock = () => products.filter(p => p.quantity <= p.reorderLevel);

  return (
    <InventoryContext.Provider value={{
      products, loading, error,
      fetchProducts, addProduct, updateProduct, deleteProduct, getLowStock,
    }}>
      {children}
    </InventoryContext.Provider>
  );
}

export const useInventoryContext = () => useContext(InventoryContext);
