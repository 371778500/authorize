define(['text!pages/learn/course/course.html','css!pages/learn/course/course','pages/learn/course/coursemeta','uuitree','uuigrid'],function(html) {
	var init = function(element){
		var treelistUrl = ctx+'/coursetype/list';
		var treedelUrl = ctx+'/coursetype/del';
		var treesaveUrl = ctx+'/coursetype/save';
		
		var tablelistUrl = ctx+'/Course/SearchCourse';
		var tabledelUrl = ctx+'/Course/delCourse';
		var tablesaveUrl = ctx+'/Course/save';
		var tableuseUrl = ctx+'/Course/UseCourse';
		var tablestopUrl = ctx+'/Course/stopUseCourse';
		
		var uploadUrl = ctx+'/file/upload';
	
		var viewModel = {
				flag:false,
				chapterFlag:false,
				app:{},
				/* 数据模型 */
				draw:1,
				totlePage:0,
				pageSize:5,
				totleCount:0,

				/* 树数据 */
				courseTypeData : new u.DataTable(metaCourseType),
				
				/* 编辑框树数据 */
				courseTypeDataNew : new u.DataTable(metaCourseType),
				
				/* 课程数据 */
				courseData : new u.DataTable(metaCourse),
				
				/* 编辑课程数据 */
				courseDataNew : new u.DataTable(metaCourse),
				
				/* 树设置 */
				treeSetting : {
					view : {
						showLine : false,
						selectedMulti : false
					},
					callback : {
						onClick : function(e, id, node) {
							treeid=[];
							viewModel.event.getAllChildren(node,treeid);
						    var pid = node.pId;
						    viewModel.event.loadCourse(treeid); //加载该树下的所以右侧列表数据
						}
					}
				
				},
				event:{
					
	                //清除datatable数据
	                clearDt: function (dt) {
	                	dt.removeAllRows();
	                	dt.clear();
	                },
					
					/* 获得树节点的所有子节点 */
					getAllChildren:function(node,childrenlist){
						childrenlist.push(node.id)
						if(node.children){
							var i;
							for(i=0;i<node.children.length;i++){
								viewModel.event.getAllChildren(node.children[i],childrenlist);
							}
						}
					},
					//组装list
					genDataList:function(data){
						var datalist = [];
						datalist.push(data);
						return datalist ;
					},
					//组装选中的课程的id
					genSelectIds:function(rows){
						var datalist = "";
						for(var i=0;i<rows.length;i++){
							datalist+=rows[i].getSimpleData().pkCourse;
							if(i!=rows.length-1){
								datalist+=",";
							}
						}
						return datalist ;
					},
					/* 加载课程   参数为课程分类ids*/
					loadCourse:function(ids){
						var jsonData={
								pageIndex:viewModel.draw,
								pageSize:viewModel.pageSize,
								Backstage:1
							};
						/*右表的上面详细信息显示*/
						var infoDiv = document.getElementById('infoPanel');
						var dtVal = viewModel.courseTypeData.getValue('courseTypeName');
						infoDiv.innerHTML = dtVal;
						//end
						$(element).find("#search").each(function(){
							if(this.value == undefined || this.value =='' || this.value.length ==0 ){
								//不执行操作
							}else{
								jsonData['coursename'] =  this.value.replace(/(^\s*)|(\s*$)/g, "");  //去掉空格
							}
						});
						if(ids){
							if(ids!=''||ids.length!=0){
								jsonData['courseTypeIds'] = ids.join();
							}
						}
						$.ajax({
							type : 'get',
							url : tablelistUrl,
							dataType : 'json',
							data:jsonData,
							contentType: 'application/json;charset=utf-8',
							success : function(res) {
								if(res){
									if( res.success =='success'){
										if(!res.detailMsg.data){
											viewModel.totleCount=0;
											viewModel.totlePage=1;
											viewModel.event.comps.update({totalPages:viewModel.totlePage,pageSize:viewModel.pageSize,currentPage:viewModel.draw,totalCount:viewModel.totleCount});
											viewModel.courseData.removeAllRows();
											viewModel.courseData.clear();
										}else{
											viewModel.totleCount=res.detailMsg.data.totalElements;
											viewModel.totlePage=res.detailMsg.data.totalPages;
											viewModel.event.comps.update({totalPages:viewModel.totlePage,pageSize:viewModel.pageSize,currentPage:viewModel.draw,totalCount:viewModel.totleCount});
											viewModel.courseData.removeAllRows();
											viewModel.courseData.clear();
											viewModel.courseData.setSimpleData(res.detailMsg.data.content,{unSelect:true});
										}
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'请求错误',btnText:'确定'});									
									}
								}else{
									u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
								}

							},
							error:function(er){
								u.messageDialog({msg:'请求超时',title:'请求错误',btnText:'确定'});
							}
						})
					},
					loadTree:function(){
						$.ajax({
							type : 'get',
							url : treelistUrl,
							dataType : 'json',
							success : function(res) {
								if(res){
									if( res.success =='success'){
										if(res.detailMsg.data){
											viewModel.courseTypeData.removeAllRows();
											viewModel.courseTypeData.clear();
											viewModel.courseTypeData.setSimpleData(res.detailMsg.data,{unSelect:true});
	                                        $("#tree2")[0]['u-meta'].tree.expandAll(true);
										}
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'请求错误',btnText:'确定'});									
									}
								}else{
									u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
								}

							},
							error:function(er){
								u.messageDialog({msg:'请求超时',title:'请求错误',btnText:'确定'});
							}
						})
					},
					//新增和更新组织树
					saveTree:function(data){
						$.ajax({
							type : 'post',
							url : treesaveUrl,
							dataType : 'json',
							contentType : "application/json",
							data : JSON.stringify(data),
							success : function(res) {
								if(res){
									if( res.success =='success'){
										u.messageDialog({msg: '保存成功！', btnText: '确定'});
										viewModel.event.loadTree();
										md.close();
									}else{
										var msg = "";
										if(res.success == 'fail_global'){
											msg = res.message;
										}else{
											for (var key in res.detailMsg) {
												msg += res.detailMsg[key] + "<br/>";
											}
										}
										u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
									}
								}else{
									u.messageDialog({msg:'没有返回数据',title:'操作提示',btnText:'确定'});
								}
							}
						})
						
						},
						//删除组织树
						deleteTree: function(data) {
				            var json = {
									"id":data
							};
				            $.ajax({
				                url: treedelUrl,
				                data: json,
				                dataType: 'json',
				                type: 'get',
				                contentType: 'application/json',
				                success: function (res) {
				                	if(res){
				                	       if (res.success == 'success') {
				                	    	   viewModel.courseTypeData.removeRow(viewModel.courseTypeData.getSelectedIndexs());
				                	    	   u.messageDialog({msg:'删除成功',title:'操作提示',btnText:'确定'});
				    	                    } else {
												var msg = "";
											   if(res.success == 'fail_global'){
												   msg = res.message;
												   if(msg=='Data had been referenced,remove is forbidden!'){
													   msg='该部门含有子部门不能删除';   
												   }
											   }else{
												   for (var key in res.detailMsg) {
													   msg += res.detailMsg[key] + "<br/>";
												   }
											   }
											   u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
				    	                    }
				                	}else{
										u.messageDialog({msg:'无返回数据',title:'操作提示',btnText:'确定'});
									}
				             
				                },
								error:function(er){
									u.messageDialog({msg:'操作请求失败，'+er,title:'操作提示',btnText:'确定'});
								}
				            });
				        },
				        //更新和保存人员
				        saveCoures:function(data){
							$.ajax({
								type : 'post',
								url : tablesaveUrl,
								dataType : 'json',
								contentType : "application/json",
								data : JSON.stringify(data),
								success : function(res) {
									if(res){
										if( res.success =='success'){
											u.messageDialog({msg: '保存成功！', btnText: '确定'});
											viewModel.event.loadCourse(treeid);
											md.close();
										}else{
											var msg = "";
											if(res.success == 'fail_global'){
												msg = res.message;
											}else{
												for (var key in res.detailMsg) {
													msg += res.detailMsg[key] + "<br/>";
												}
											}
											u.messageDialog({msg:msg,title:'操作提示',btnText:'确定'});
										}
									}else{
										u.messageDialog({msg:'没有返回数据',title:'操作提示',btnText:'确定'});
									}
								}
							});
				        },
				        //删除课程
				        delCourse:function(data){
							var list={
									"courseIdList":data
							}
							$.ajax({
								type : 'get',
								url : tabledelUrl,
								dataType : 'json',
								contentType : "application/json",
								data : list,
								success : function(res) {
									if( res.success =='success'){
										u.messageDialog({msg: '删除成功！', btnText: '确定'});
										viewModel.event.loadCourse(treeid);
										 //md.close();
									}else{
										u.messageDialog({msg: '删除失败！', btnText: '确定'});
									}
								}
							})
				        },
				        //启用课程
				        useCourse:function(data){
							var list={
									"courseIdList":data
							}
							$.ajax({
								type : 'get',
								url : tableuseUrl,
								dataType : 'json',
								contentType : "application/json",
								data : list,
								success : function(res) {
									if( res.success =='success'){
										u.messageDialog({msg: '启用成功！', btnText: '确定'});
										viewModel.event.loadCourse(treeid);
										 //md.close();
									}else{
										u.messageDialog({msg: '启用失败！', btnText: '确定'});
									}
								}
							})
				        },
				        //停用课程
				        stopCourse:function(data){
							var list={
									"courseIdList":data
							}
							$.ajax({
								type : 'get',
								url : tablestopUrl,
								dataType : 'json',
								contentType : "application/json",
								data : list,
								success : function(res) {
									if( res.success =='success'){
										u.messageDialog({msg: '停用成功！', btnText: '确定'});
										viewModel.event.loadCourse(treeid);
										 //md.close();
									}else{
										u.messageDialog({msg: '停用失败！', btnText: '确定'});
									}
								}
							})
				        },
				    //分页相关
					pageChange:function(){
						viewModel.event.comps.on('pageChange', function (pageIndex) {
							viewModel.draw = pageIndex + 1;
							viewModel.event.loadCourse(treeid);
						});
					},
					sizeChange:function(){
						viewModel.event.comps.on('sizeChange', function (arg) {
							viewModel.pageSize = parseInt(arg);
							viewModel.draw = 1;
							viewModel.event.loadCourse(treeid);
						});
					},
					
					//页面初始化
					pageInit : function() {
						treeid=[];
						
						viewModel.app = u.createApp({
							el :element /* Document.body */,
							model : viewModel
						})

						//分页初始化	
						var paginationDiv = $(element).find('#pagination')[0];
						this.comps=new u.pagination({el:paginationDiv,jumppage:true});
						this.comps.update({pageSize: 5 });  //默认每页显示5条数据
						this.loadTree();
						viewModel.event.pageChange();
						viewModel.event.sizeChange();	
						viewModel.event.loadCourse(treeid);
	                    //回车搜索
	                    $('.input_enter').keydown(function(e){
	                        if(e.keyCode==13){
	                        	viewModel.event.searchClick()
	                        	u.stopEvent(e);
	                        }
	                    });
					
					},
					addCouresTypeClick:function(){
                        $('#institcode').removeAttr("readonly");
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>新增课程分类</h4>");
						viewModel.event.clearDt(viewModel.courseTypeDataNew);
						var row = viewModel.courseTypeData.getSelectedRows()[0];
						
						if(row){
							var parentId = row.getValue('pkCourseType');
							var parentName = row.getValue('courseTypeName');
							var parentcode = row.getValue('courseTypeCode');
						}
						
						var newr = viewModel.courseTypeDataNew.createEmptyRow();
			            viewModel.courseTypeDataNew.setRowSelect(newr);
			            
			            if(row){
			            	var newrow = viewModel.courseTypeDataNew.getSelectedRows()[0];
			            	newrow.setValue('pkCourseTypeParentid',parentId);
			            	newrow.setValue('pkCourseTypeParentName',parentName);
			            	newrow.setValue('courseTypeCode',parentcode);
			            }

			            window.md = u.dialog({id:'add_depart',content:"#dialog_content_instit",hasCloseMenu:true});
					},
					editCouresTypeClick:function(){
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>编辑课程分类</h4>");
						viewModel.event.clearDt(viewModel.courseTypeDataNew);
						var row = viewModel.courseTypeData.getSelectedRows()[0];
						if(row){
							if(row.data.pkCourseTypeParentid.value){
								row.getValue('pkCourseTypeParentid')=="root"?
										row.setValue('pkCourseTypeParentName','上级'):
										row.setValue('pkCourseTypeParentName',$("#tree2")[0]['u-meta'].tree.getNodeByParam('id',row.getValue('pkCourseTypeParentid')).name);
							}

							viewModel.courseTypeDataNew.setSimpleData(viewModel.courseTypeData.getSimpleData({type: 'select'}));
							$('#institcode').attr("readonly","readonly");
							window.md = u.dialog({id:'edit_depart',content:"#dialog_content_instit",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delCouresTypeClick:function(){
						var row = viewModel.courseTypeData.getSelectedRows()[0]
						if(row){
							var data = row.getSimpleData();
							u.confirmDialog({
					            msg: "是否删除"+data.courseTypeName+"?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.deleteTree(data.pkCourseType);
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					saveCouresTypeClick:function(){
						var data = viewModel.courseTypeDataNew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#dialog_content_instit')[0])) {
	                        return;
	                    }
                        viewModel.event.saveTree(data);
					},
					saveCourseClick:function(){
						var data = viewModel.courseDataNew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#dialog_content_course')[0])) {
	                        return;
	                    }
                        viewModel.event.saveCoures(data);
					},
					canceCouresTypeClick:function(){
						md.close();
					},
					canceupload:function(){
						$("#file-list div.c-cart-table-row").remove();
						pluploadmd.close();
					},
					openupload:function(){
						if(event.toElement.id=="courseIconUrl"){
							viewModel.flag=false;
						}else{
							viewModel.flag=true;
						}
						if(event.toElement.id=="chapterUrl"){
							viewModel.chapterFlag=false;
						}else{
							viewModel.chapterFlag=true;
						}
						$('#upload_files').html("");
						window.pluploadmd = u.dialog({id:'upload_depart',content:"#dialog_content_upload",hasCloseMenu:true});
					},
					addCourseClick:function(){
						$('#dialog_content_course').find('.u-msg-title').html("<h4>新增课程</h4>");
						viewModel.event.clearDt(viewModel.courseDataNew);
						var row = viewModel.courseTypeData.getSelectedRows()[0];
						if(row){
							
							var newr = viewModel.courseDataNew.createEmptyRow();
							viewModel.courseDataNew.setRowSelect(newr);
							var newrow = viewModel.courseDataNew.getSelectedRows()[0];
							newrow.setValue('pkCourseType',row.getValue('pkCourseType'));
							
							$("#upload_files").html("");
							window.md = u.dialog({id:'add_man',content:"#dialog_content_course",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择课程类型！',title:'操作提示',btnText:'确定'});
						}
					},
					editCourseClick:function(){
						$('#dialog_content_course').find('.u-msg-title').html("<h4>编辑课程</h4>");
						viewModel.event.clearDt(viewModel.courseDataNew);
						var rows = viewModel.courseData.getSelectedRows();
						
						if(rows.length==1){
							viewModel.courseDataNew.setSimpleData(viewModel.courseData.getSimpleData({type: 'select'}));
							window.md = u.dialog({id:'edit_man',content:"#dialog_content_course",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择一条要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delCourseClick:function(){
						var rows = viewModel.courseData.getSelectedRows();
						if(rows.length!=0){
							var data = viewModel.event.genSelectIds(rows);
							u.confirmDialog({
					            msg: "是否删除选中信息?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.delCourse(data);
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					 //启用
			        isUseCourseClick:function(){
			        	var rows = viewModel.courseData.getSelectedRows();
						if(rows.length!=0){
							var data = viewModel.event.genSelectIds(rows);
							u.confirmDialog({
					            msg: "是否启用选中课程?",
					            title: "启用确认",
					            onOk: function () {
					                viewModel.event.useCourse(data);
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要启用的课程！',title:'操作提示',btnText:'确定'});
						}
			        },
			        //停用
			        stopCourseClick:function(){
			        	var rows = viewModel.courseData.getSelectedRows();
						if(rows.length!=0){
							var data = viewModel.event.genSelectIds(rows);
							u.confirmDialog({
					            msg: "是否停用选中课程?",
					            title: "停用确认",
					            onOk: function () {
					                viewModel.event.stopCourse(data);
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要停用的课程！',title:'操作提示',btnText:'确定'});
						}
			        },
					searchClick:function(){
						viewModel.draw = 1; 
						viewModel.event.loadCourse(treeid);
					}
					
				},
				getCourseTypeId:function(){
					if(viewModel.courseDataNew.getCurrentRow().data.pkCourse.value){
						return viewModel.courseDataNew.getCurrentRow().data.pkCourseType.value;
					}else{
						return viewModel.courseTypeData.getSelectedRows()[0].getValue('pkCourseType');
					}
				},
				fileupload:function(){
					viewModel.uploader=new plupload.Uploader({ //实例化一个plupload上传对象
						//上传插件初始化选用那种方式的优先级顺序，如果第一个初始化失败就走第二个，依次类推
						runtimes: 'html5,flash,silverlight,html4',
						browse_button : 'browse',
						url : uploadUrl,
						flash_swf_url : 'vendor/plupload/Moxie.swf',
						silverlight_xap_url : 'vendor/plupload/Moxie.xap',
						//配置上传相关参数
//						filters: {
//							max_file_size: '1000mb',
//							mime_types: [{
//								title: "file",
//								extensions: "txt,mp4,jpg,jpeg,gif,png"
//							}]
//						},
//						
//						//多选文件上传
//						multi_selection: false,
						
						//初始化
						init: {
							//上传前配置参数,该参数用于传达到后台
							BeforeUpload: function(up, files) {
								viewModel.uploader.setOption("multipart_params", {
									"coursetypeid": viewModel.getCourseTypeId(),
									"fileid":files.id
								});
							},
							//上传开始
							FilesAdded: function(up, files) {
								for(var i = 0, len = files.length; i<len; i++){
									var file_name = files[i].name; //文件名
									//构造html来更新UI
									var html= '<tr id="' + files[i].id +'">'+
													'<td>' + file_name + '</td>'+
													'<td><div class="progress" style="height:25px;"><div  id="file-' + files[i].id +'" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;background-color: #ff7800"> 0%</div></div></td>'+
													'<td><button class="btn btn-xs green" type="button"  onclick="removefile(\''+files[i].id+'\')" >删除</button></td>'+
											  '</tr>';
									$(html).appendTo('#upload_files');
								}
								
								viewModel.uploader.start(); //开始上传
							},
							//上传中   绑定文件上传进度事件 UploadProgress
							UploadProgress: function(up, file) {
								$('#file-'+file.id).css('width',file.percent + '%');//控制进度条
								$('#file-'+file.id).attr('aria-valuenow',file.percent);
								$('#file-'+file.id).text(file.percent + '%');
							},
							
							//上传出错
							Error: function(up, err) {
								u.messageDialog({msg:'上传出错！',title:'操作提示',btnText:'确定'});
								return;
							},
							//上传完成
							UploadComplete: function(up, file) {
								$('#file-'+file[file.length-1].id).css('width',file[file.length-1].percent + '%');//控制进度条
								$('#file-'+file[file.length-1].id).attr('aria-valuenow',file[file.length-1].percent);
								$('#file-'+file[file.length-1].id).text(file[file.length-1].percent + '%');
								
								//回写shang传路径
								var url='doc'+'\\'+viewModel.getCourseTypeId()+'\\'+file[file.length-1].id+'\\'+file[file.length-1].name;
								var newrow = viewModel.courseDataNew.getSelectedRows()[0];
//								viewModel.flag?newrow.setValue('courseUrl',url):newrow.setValue('courseIconUrl',url);
								if(viewModel.flag && viewModel.chapterFlag){
									newrow.setValue('courseUrl',url);
								}else if(viewModel.flag){
									newrow.setValue('chapterUrl',url);
								}else{
									newrow.setValue('courseIconUrl',url);
								}
							}
						}
					});
					viewModel.uploader.init();
					//上传按钮
					$('#upload-btn').click(function(){
						viewModel.uploader.start(); //开始上传
					});
				}		
					
			};
		$(element).html(html) ;
		viewModel.event.pageInit();
		viewModel.fileupload();
		
		if(u.isIE8){
			$('.ie8-bg').css('display','block');
		}
	}
	
	
	//上传
	return {
		'template': html,
        init:init
	}
});
