import { authApi } from '../utilities/ApiUtils';
 
export const AuthService = {
  login:  (username, password) => authApi.post('/auth/login',  { username, password }),
  signup: (user)              => authApi.post('/auth/signup', user),
  logout: ()                  => authApi.post('/auth/logout', {}),
  me:     ()                  => authApi.get('/auth/me'),
};