export const environment = {
  production: true,
  // BASE_URL: 'http://103.129.247.232:8090/dal_prod_services/api/v1',
  BASE_URL: 'http://103.129.247.232:9090/dal_prod_services/api/v1',

  /*  API Endpoints */

  // Modules List

  user: '/user',
  authUsers: '/auth-users',
  component: '/component',
  componentPermission: '/component-permission',
  module: '/module',
  moduleForRole: '/module-for-role',
  subModule: '/sub-module',
  role: '/role',
  roleBasedPermission: '/role-based-permission',
  userBasedPermission: '/user-based-permission',
  visitor: '/visitor',
  visitors: '/visitors',
  approvalWorkFlowPermission: '/approval-work-flow-permission',
  approvalWorkFlow: '/approval-work-flow',
  approvalWorkFlowFor: '/approval-work-flow-for',
  approvalWorkFlowForm: '/approval-work-flow-form',
  approvalWorkFlowStatus: '/approval-work-flow-status',
  approvalWorkFlowSteps: '/approval-work-flow-steps',
  userFormApprovalFlow: '/user-form-approval-flow',

  // User and Auth Module

  login: '/login',
  signIn: '/sign-in',
  register: '/register',
  resetPassword: '/reset-user-password',
  updateProfileImage: '/update-profile-image',
  hrEmployees: '/hr-employees',
  changePassword: '/change-password',
  items: '/items',
  employees: '/employees',
  employeeType: '/employee-type',

  // Common attributes

  add: '/add',
  create: '/create',
  createOrUpdate: '/create-or-update',
  update: '/update',
  delete: '/delete',
  list: '/list',
  count: '/count',
  findAll: '/find-all',
  findByUniqueKey: '/find-by-unique-key',
  detailsById: '/details-by-id',
  findByYearAndMonth: '/find-by-year-month',
  exportData: '/export-data',
  generateReport: '/generate-report',
  generatePdfReport: '/generate-pdf-report',
  generateXlsxReport: '/generate-xlsx-report',
  generatedProductionNo: '/generate-production-no',

  // Component Modules

  findProductById: '/find-product-by-id',
  findAccessPermission: '/find-access-permission',
  findAllPermissiveModulesAndComponents:
    '/find-all-permissive-modules-and-components',
  findAllComponentPermissionsByComponentId:
    '/find-all-component-permissions-by-component-id',
  findAllComponentsByModuleId: '/find-all-components-by-module-id',
  findAllOrphanComponentsByModuleId: '/find-all-orphan-components-by-module-id',
  findAllComponentsBySubmoduleId: '/find-all-components-by-sub-module-id',
  findComponentByPath: '/find-component-by-path',

  // Modules

  findModuleById: '/find-module-by-id',
  findAllModuleForRolesByModuleId: '/find-all-module-for-roles-by-module-id',
  findModuleComponents: '/find-module-components',

  // Submodules

  findSubmoduleById: '/find-sub-module-by-id',
  findAllSubmodulesByModuleId: '/find-all-sub-modules-by-module-id',

  // Role Modules

  findRoleById: '/find-role-by-id',
  findAllByRoleId: '/find-all-by-role-id',
  findComponentPermission: '/find-component-permission',
  findAllSidenavElements: '/find-all-sidenav-elements',

  // User Based Permissions Module
  findAllByUserAndRoleId: '/find-all-by-user-and-role-id',
  revertToDefaultRole: '/revert-to-default-role',

  // Settings Module

  visit: '/visit',
  logList: '/log-list',
  getByForm: '/get-by-form',
  getNextStep: '/get-next-step',
  getPreviousStep: '/get-previous-step',
};
