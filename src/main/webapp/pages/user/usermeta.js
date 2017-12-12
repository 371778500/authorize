var today = new Date();
var todayStr = today.getFullYear() + '/' + (today.getMonth() + 1) + '/' + today.getDate();

var  userMetaDt = {
		meta: {
			//用户主键
			userid: {
				type: 'int'
			},
			//用户登录帐号
			username: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			//用户绑定员工编码
			usercode: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			
			//用户姓名
			name: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			//密码
			password: {
				type: 'string',
				notipFlag: true,
				hasSuccess: true,
				required: true
			},
			//是否可用
			enable: {
				type: 'string'
			}
		}
};
//end user metaDt
var  roleMetaDt = {
		meta: {
			roleid: {
				type: 'int'
			},
			rolecode: {
				type: 'string'
			},
			rolename: {
				type: 'string'
			},
			createuser:{
				type: 'string'
			},
			createtime:{
				type: 'string'
			}
		}
};
