define(['text!pages/user/user.html', 'css!pages/user/user','pages/user/usermeta', 'uuitree', 'uuigrid', 'config/sys_const'], 
		function (template,metaModel) {

    //开始初始页面基础数据
    var init = function (element, params) {
    	var searchURL=ctx + '/user/queryuser';
    	var addURL=ctx + "/user/saveuser";
    	
    	
        var viewModel = {
            draw: 1,//页数(第几页)
            pageSize: 5,
            /*用户表格数据  */
            userDa: new u.DataTable(userMetaDt),
            /*角色表格数据  */
            roleDa: new u.DataTable(roleMetaDt),
            event: {
            	
            }
        };
    }    

    return {
        'model': init.viewModel,
        'template': template,
        'init': function (element, arg) {
            init(element, arg);
            /*回车搜索*/
            $('.search-enter').keydown(function (e) {
                if (e.keyCode == 13) {
                    $('#user-action-search').trigger('click');
                    u.stopEvent(e);
                }
            });
        }
    }
});
//end define
