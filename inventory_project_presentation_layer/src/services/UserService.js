import { authApi } from '../utilities/ApiUtils';
 
export const UserService = {
  getAll:   ()             => authApi.get('/users'),
  getById:  (id)           => authApi.get(`/users/${id}`),
  create:   (user)         => authApi.post('/auth/signup', user),
  update:   (id, data)     => authApi.put(`/users/${id}`, data),
  delete:   (id)           => authApi.delete(`/users/${id}`),
  changePassword: (data)   => authApi.put('/users/password', data),
};