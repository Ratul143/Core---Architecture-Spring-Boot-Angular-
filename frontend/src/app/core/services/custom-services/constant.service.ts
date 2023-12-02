import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantService {
  constructor() { }

  deviceStatusList = [
    { id: 'Assigned',    text: 'Assigned' },
    { id: 'Unassigned',  text: 'Unassigned' }
  ];
  deviceStatusAllList = [
    { id: 'Assigned',    text: 'Assigned' },
    { id: 'Unassigned',  text: 'Unassigned' },
    { id: 'Damaged',     text: 'Damaged' }
  ];

  statusList = [
    { id: 'Active',    text: 'Active' },
    { id: 'Inactive',  text: 'Inactive' }
  ];

  status_List = [
    { id: 'Application Submitted',    text: 'Application Submitted' },
    { id: 'DataUpdated',  text: 'DataUpdated' }
  ]; 
  status_List_all = [
    { id: 'Approved',  text: 'Approved' }
  ];

  selectionListForMenuConfig = [
    { id: 'CSP',    text: 'Outlet' },
    { id: 'Teller',  text: 'Teller' },
    { id: 'BranchMaker',  text: 'Branch Maker' },
    { id: 'AgentCao',  text: 'Agent Cao' },
    { id: 'DoerOpBdo',  text: 'Doer OP BDO' }
  ];

  glTypeList = [
    { id: 'CR',   text: 'Credit' },
    { id: 'DR',   text: 'Debit' },
    { id: 'VAT',  text: 'VAT' }
  ];

  agentTypeList = [
    { id: 'Agent',    text: 'Agent' },
    { id: 'BranchAgent',  text: 'Branch Agent' }
  ];
  actionTypeList = [
    { id: 'Temporary',    text: 'Temporary' },
    { id: 'Permanent',  text: 'Permanent' }];

  userTypeList = [
    { id: 'BankStaff',  title: 'BankStaff' },
    { id: 'DoerStaff',  title: 'DoerStaff' }
  ];;

  genderList = [
    { id: 'Female',    text: 'Female' },
    { id: 'Male',      text: 'Male' },
    { id: 'Other',     text: 'Other' }
  ];

  listLinks = [
    { id: 'getList',    text: 'All List' },
    { id: 'getApprovalList',  text: 'Waiting for Approval' },
    { id: 'getMyLockedList',  text: 'My Locked List' }
  ];

  popularPolicyColumns = [
    { id: 'policyHolderName', text: 'Policy Holder Name' },
    { id: 'policyStartDate',  text: 'Policy Start Date'},
    { id: 'term',             text: 'Term'},
    { id: 'premiumMode',      text: 'Premium Mode'},
    { id: 'assuredSum',       text: 'Assured Sum' },
    { id: 'totalPremium',     text: 'Total Premium'},
    { id: 'lifePremium',      text: 'Life Premium'},
    { id: 'mobileNumber',     text: 'Mobile Number'},
    { id: 'dateOfBirth',      text: 'Date of Birth'},
    { id: 'productName',      text: 'Product Name'},
    { id: 'address',          text: 'Address'},
    { id: 'gender',           text: 'Gender'},
    { id: 'email',            text: 'Email'},
    { id: 'supplyPremium',    text: 'Supply Premium'},
    { id: 'externalLoad',     text: 'External Load'},
    { id: 'planNumber',       text: 'Plan Number'}

  ];

  popularPaymentColumns = [
    { id: 'transactionId',    text: 'Transaction Id' },
    { id: 'receivedAmount',   text: 'Received Amount' },
    { id: 'receivedDate',     text: 'Received Date'},
    { id: 'agentId',          text: 'Agent'},
    { id: 'collectingBranch', text: 'Collecting Branch'},
    { id: 'servicingBranch',  text: 'Servicing Branch' },
    { id: 'smsStatus',        text: 'SMS Status' }
  ];

  userTypeJson = [
    { id: 'IdraStaff',    text: 'Idra Staff' },
    { id: 'InsuranceStaff',  text: 'Insurance Staff' }
  ];

  productTypeList = [
    { id: 'CURRENT', text: 'CURRENT'},
    { id: 'SAVINGS', text: 'SAVINGS'},
    { id: 'SCHEME', text: 'SCHEME'},
    { id: 'TIME', text: 'TIME'}
  ];

  country = [
    {text: 'Afghanistan', id: 'AF'},
    {text: 'Argentina', id: 'AR'},
    {text: 'Australia', id: 'AU'},
    {text: 'Bahrain', id: 'BH'},
    {text: 'Bangladesh', id: 'BD'},
    {text: 'Bhutan', id: 'BT'},
    {text: 'Canada', id: 'CA'},
    {text: 'China', id: 'CN'},
    {text: 'Ecuador', id: 'EC'},
    {text: 'Egypt', id: 'EG'},
    {text: 'France', id: 'FR'},
    {text: 'Germany', id: 'DE'},
    {text: 'Hong Kong', id: 'HK'},
    {text: 'India', id: 'IN'},
    {text: 'Italy', id: 'IT'},
    {text: 'Japan', id: 'JP'},
    {text: 'Kuwait', id: 'KW'},
    {text: 'Malaysia', id: 'MY'},
    {text: 'Maldives', id: 'MV'},
    {text: 'Myanmar', id: 'MM'},
    {text: 'Nepal', id: 'NP'},
    {text: 'New Zealand', id: 'NZ'},
    {text: 'Oman', id: 'OM'},
    {text: 'Pakistan', id: 'PK'},
    {text: 'Qatar', id: 'QA'},
    {text: 'Saudi Arabia', id: 'SA'},
    {text: 'Singapore', id: 'SG'},
    {text: 'South Africa', id: 'ZA'},
    {text: 'Spain', id: 'ES'},
    {text: 'Sri Lanka', id: 'LK'},
    {text: 'Sweden', id: 'SE'},
    {text: 'Switzerland', id: 'CH'},
    {text: 'United Arab Emirates', id: 'AE'},
    {text: 'United Kingdom', id: 'GB'},
    {text: 'United States', id: 'US'},
  ];

  ddlMethods = [{text: 'Absolute', id: 'Absolute'}, {text: 'Percentage', id: 'Percentage'}];

  calendarJsonData =  {
      weeksName: [
        {
          shortName: 'Sun',
          fullName: 'Sunday',
          inx: 0,
          enabled: false,
          startTime: '09:35',
          endTime: '17:35'
        },
        {
          shortName: 'Mon',
          fullName: 'Monday',
          inx: 1,
          enabled: false,
          startTime: '10:55',
          endTime: '17:55'
        },
        {
          shortName: 'Tue',
          fullName: 'Tuesday',
          inx: 2,
          enabled: false,
          startTime: '09:02',
          endTime: '17:00'
        },
        {
          shortName: 'Wed',
          fullName: 'Wednesday',
          inx: 3,
          enabled: false,
          startTime: '09:46',
          endTime: '17:12'
        },
        {
          shortName: 'Thu',
          fullName: 'Thursday',
          inx: 4,
          enabled: false,
          startTime: '09:34',
          endTime: '17:44'
        },
        {
          shortName: 'Fri',
          fullName: 'Friday',
          inx: 5,
          enabled: true,
          startTime: '09:47',
          endTime: '17:32'
        },
        {
          shortName: 'Sat',
          fullName: 'Saturday',
          inx: 6,
          enabled: true,
          startTime: '09:48',
          endTime: '17:30'
        }
      ],
      holidaysName: [
        {
          slNo: '0',
          name: 'International Mother Language Day',
          description: 'International Mother Language Day',
          effectiveDate: '2019-02-21'
        },
        {
          slNo: '1',
          name: 'Birthday of Bangabandhu Sheikh Mujibur Rahman',
          description: 'Birthday of Bangabandhu Sheikh Mujibur Rahman',
          effectiveDate: '2019-03-17'
        },
        {
          slNo: '2',
          name: 'Independence Day',
          description: 'Independence Day',
          effectiveDate: '2019-03-26'
        },
        {
          slNo: '3',
          name: 'Bangla Noboborsho',
          description: 'Bangla Noboborsho',
          effectiveDate: '2019-04-14'
        },
        {
          slNo: '4',
          name: 'International Labour Day',
          description: 'International Labour Day',
          effectiveDate: '2019-05-01'
        },
        {
          slNo: '5',
          name: 'National Mourning Day',
          description: 'Sheikh Mujibur Rahman and his family were killed in 1975',
          effectiveDate: '2019-08-15'
        },
        {
          slNo: '6',
          name: 'Victory Day',
          description: 'Victory Day',
          effectiveDate: '2019-12-16'
        },
        {
          slNo: '7',
          name: 'Christmas Day',
          description: 'Christmas Day',
          effectiveDate: '2019-12-25'
        }
      ]
    };
}

