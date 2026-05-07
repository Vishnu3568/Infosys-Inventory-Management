import { useState } from 'react';
import { ValidationUtils } from '../utilities/ValidationUtils';
import { CATEGORIES } from '../utilities/Constants';
import { useAuth } from '../hooks/useAuth';

const EMPTY = { name: '', category: '', quantity: '', unitPrice: '', reorderLevel: '10', supplier: '' };

const Field = ({ label, field, type = 'text', value, onChange, error, ...rest }) => (
  <div className="form-group">
    <label>{label}</label>
    <input
      type={type}
      value={value}
      onChange={onChange}
      {...rest}
    />
    {error && <div className="error-msg">{error}</div>}
  </div>
);

export default function ProductForm({ initial = null, onSubmit, onCancel, loading = false }) {
  const { user } = useAuth();
  const isStaff = user?.role === 'STAFF';

  // Only set form state from initial/EMPTY on first mount
  const [form, setForm] = useState(() => initial || EMPTY);
  const [errors, setErrors] = useState({});

  // Always store as string to avoid input bugs
  const set = (field) => (e) => setForm(f => ({ ...f, [field]: String(e.target.value) }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    const errs = ValidationUtils.validateProduct(form);
    if (Object.keys(errs).length) { setErrors(errs); return; }
    setErrors({});
    await onSubmit({
      ...form,
      quantity: Number(form.quantity),
      unitPrice: Number(form.unitPrice),
      reorderLevel: Number(form.reorderLevel),
    });
  };

  return (
    <form onSubmit={handleSubmit} className="form-card">
      <div className="form-grid-2">
        <Field 
          label="Product Name *" 
          field="name" 
          placeholder="e.g. Wireless Mouse" 
          value={form.name} 
          onChange={set('name')} 
          error={errors.name} 
          disabled={isStaff}
        />
        <div className="form-group">
          <label>Category *</label>
          <select value={form.category} onChange={set('category')} disabled={isStaff}>
            <option value="">Select category</option>
            {CATEGORIES.map(c => <option key={c} value={c}>{c}</option>)}
          </select>
          {errors.category && <div className="error-msg">{errors.category}</div>}
        </div>
        <Field 
          label="Quantity *" 
          field="quantity" 
          type="number" 
          min="0" 
          placeholder="0" 
          value={form.quantity} 
          onChange={set('quantity')} 
          error={errors.quantity} 
        />
        <Field 
          label="Unit Price (₹) *" 
          field="unitPrice" 
          type="number" 
          min="0" 
          step="0.01" 
          placeholder="0.00" 
          value={form.unitPrice} 
          onChange={set('unitPrice')} 
          error={errors.unitPrice} 
          disabled={isStaff}
        />
        <Field 
          label="Reorder Level" 
          field="reorderLevel" 
          type="number" 
          min="0" 
          placeholder="10" 
          value={form.reorderLevel} 
          onChange={set('reorderLevel')} 
          error={errors.reorderLevel} 
          disabled={isStaff}
        />
        <Field 
          label="Supplier *" 
          field="supplier" 
          placeholder="Supplier name" 
          value={form.supplier} 
          onChange={set('supplier')} 
          error={errors.supplier} 
          disabled={isStaff}
        />
      </div>

      <div className="form-actions">
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Saving…' : (initial ? 'Update Product' : 'Add Product')}
        </button>
        {onCancel && (
          <button type="button" className="btn btn-ghost" onClick={onCancel}>
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}