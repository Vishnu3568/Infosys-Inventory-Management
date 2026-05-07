export const BUSINESS_API_BASE_URL = process.env.REACT_APP_BUSINESS_API_URL || 'http://localhost:8082/api';
export const AUTH_API_BASE_URL = process.env.REACT_APP_AUTH_API_URL || 'http://localhost:8084/api/v1';
export const API_BASE_URL = BUSINESS_API_BASE_URL;
 
export const ROLES = {
  ADMIN:   'ADMIN',
  SUPPLIER: 'SUPPLIER',
  STAFF: 'STAFF',

};
 
export const TOKEN_KEY    = 'inv_token';
export const USER_KEY     = 'inv_user';
 
export const LOW_STOCK_THRESHOLD = 10;
 
export const CATEGORIES = [
  'Electronics',
  'Stationery',
  'Furniture',
  'Clothing',
  'Food & Beverages',
  'Tools & Hardware',
  'Medical',
  'Other',
];
 
export const REPORT_TYPES = {
  SUMMARY:    'summary',
  LOW_STOCK:  'low-stock',
  VALUE:      'value',
  CATEGORIES: 'categories',
};