define(['text!pages/exam/pagemanage/KnowledgeTypeVo.html','css!pages/exam/pagemanage/KnowledgeTypeVo.css','pages/exam/pagemanage/meta','uuitree','uuigrid'],function(html) {
	
	var init = function(element){
		var treelistUrl = ctx+'/KnowledgeTypeVo/list';
		var treedelUrl = ctx+'/KnowledgeTypeVo/del';
		var treesaveUrl = ctx+'/KnowledgeTypeVo/save';
		
		var tablelistUrl = ctx+'/PaperMainVo/list';
		var tabledelUrl = ctx+'/PaperMainVo/del';
		var tablesaveUrl = ctx+'/PaperMainVo/save';
		var uploadUrl = ctx+'/upload/upload';//上传Excel URL
			
		var viewModel = {
				app:{},
				/* 数据模型 */
				draw:1,
				totlePage:0,
				pageSize:5,
				totleCount:0,

				/* 树数据 */
				KnowledgeTypeVodata : new u.DataTable(metaTree),
				
				/* 编辑框树数据 */
				KnowledgeTypeVodatanew : new u.DataTable(metaTree),
				
				/* 试卷数据 */
				PaperMainVodata : new u.DataTable(metaPaperMainVo),
				
				/* 试卷数据 */
				PaperMainVodatanew : new u.DataTable(metaPaperMainVo),
				
                //是否启用	0：未用， 1：已启用			
				PaperMainVo_using_status:[{name:"是",value: "1"}, {name:"否",value: "0"}],
				//是否可查看答案  1：可以查看，  0：不能查看
				PaperMainVo_checkanswer_status:[{name:"是",value: "1"}, {name:"否",value: "0"}],
				//是否可查看分数  1：可以查看分数，  0：不可查看分数
				PaperMainVo_checkscore_status:[{name: "是", value: "1"}, {name: "否", value: "0"}],
				
				//试卷上传数据
				UpPaper:{"pk_exam_knowledge_type":"","pk_exam_paper_main":""},
				
				
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
						   	viewModel.event.loadTable(treeid);
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
                    //加载试卷信息                    
					loadTable:function(instit){
						var jsonData={
								pageIndex:viewModel.draw,
								pageSize:viewModel.pageSize,
								Backstage:1
							};
						/*右表的上面详细信息显示*/
						var infoDiv = document.getElementById('infoPanel');
						var dtVal = viewModel.KnowledgeTypeVodata.getValue('knowlege_type_name');
						infoDiv.innerHTML = dtVal;
						//end
						$(element).find("#search").each(function(){
							if(this.value == undefined || this.value =='' || this.value.length ==0 ){
								//不执行操作
							}else{
								//按试卷名搜索
								jsonData['search_searchParam'] =  this.value.replace(/(^\s*)|(\s*$)/g, "");  //去掉空格
							}
						});
						if(instit){
							if(instit!=''||instit.length!=0){
								jsonData['search_fk_id_papermainvo'] = instit.join();
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
											viewModel.PaperMainVodata.removeAllRows();
											viewModel.PaperMainVodata.clear();
										}else{
											viewModel.totleCount=res.detailMsg.data.totalElements;
											viewModel.totlePage=res.detailMsg.data.totalPages;
											viewModel.event.comps.update({totalPages:viewModel.totlePage,pageSize:viewModel.pageSize,currentPage:viewModel.draw,totalCount:viewModel.totleCount});
											viewModel.PaperMainVodata.removeAllRows();
											viewModel.PaperMainVodata.clear();
											viewModel.PaperMainVodata.setSimpleData(res.detailMsg.data.content,{unSelect:true});
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
					//加载左边树列表
					loadTree:function(){
						$.ajax({
							type : 'get',
							url : treelistUrl,
							dataType : 'json',
							success : function(res) {
								if(res){
									if(res.success =='success'){
										if(res.detailMsg.data){
											viewModel.KnowledgeTypeVodata.removeAllRows();
											viewModel.KnowledgeTypeVodata.clear();
											viewModel.KnowledgeTypeVodata.setSimpleData(res.detailMsg.data,{unSelect:true});
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
					
					//组装list
					genDataList:function(data){
						var datalist = [];
						datalist.push(data);
						return datalist ;
					},
					//新增和更新组织树
					saveTree:function(data){
						var list=viewModel.event.genDataList(data);
						$.ajax({
							type : 'post',
							url : treesaveUrl,
							dataType : 'json',
							contentType : "application/json",
							data : JSON.stringify(list),
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
				        	var datalist = viewModel.event.genDataList(data);
				            var json = JSON.stringify(datalist);
				            $.ajax({
				                url: treedelUrl,
				                data: json,
				                dataType: 'json',
				                type: 'post',
				                contentType: 'application/json',
				                success: function (res) {
				                	if(res){
				                	       if (res.success == 'success') {
				                	    	   u.messageDialog({msg:'删除成功',title:'操作提示',btnText:'确定'});
				    	                    } else {
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
										u.messageDialog({msg:'无返回数据',title:'操作提示',btnText:'确定'});
									}
				             
				                },
								error:function(er){
									u.messageDialog({msg:'操作请求失败，'+er,title:'操作提示',btnText:'确定'});
								}
				            });
				        },
				        //更新和保存人员
				        saveMan:function(data){
							var list=viewModel.event.genDataList(data);
							$.ajax({
								type : 'post',
								url : tablesaveUrl,
								dataType : 'json',
								contentType : "application/json",
								data : JSON.stringify(list),
								success : function(res) {
									if(res){
										if( res.success =='success'){
											u.messageDialog({msg: '保存成功！', btnText: '确定'});
											viewModel.event.loadTable(treeid);
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
				        //删除人员
				        delMan:function(data){
							var list=viewModel.event.genDataList(data);
							$.ajax({
								type : 'post',
								url : tabledelUrl,
								dataType : 'json',
								contentType : "application/json",
								data : JSON.stringify(list),
								success : function(res) {
									if( res.success =='success'){
										u.messageDialog({msg: '删除成功！', btnText: '确定'});
										 //md.close();
									}else{
										u.messageDialog({msg: '删除失败！', btnText: '确定'});
									}
								}
							})
				        },
				    //分页相关
					pageChange:function(){
						viewModel.event.comps.on('pageChange', function (pageIndex) {
							viewModel.draw = pageIndex + 1;
							viewModel.event.loadTable(treeid);
						});
					},
					sizeChange:function(){
						viewModel.event.comps.on('sizeChange', function (arg) {
							viewModel.pageSize = parseInt(arg);
							viewModel.draw = 1;
							viewModel.event.loadTable(treeid);
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
						viewModel.event.loadTable(treeid);
						
	                    //回车搜索
	                    $('.input_enter').keydown(function(e){
	                        if(e.keyCode==13){
	                        	viewModel.event.searchClick()
	                        	u.stopEvent(e);
	                        }
	                    });
					
					},
					addinstitClick:function(){
						$('#knowlege_type_code').removeAttr("readonly");
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>新增知识类</h4>");
						viewModel.event.clearDt(viewModel.KnowledgeTypeVodatanew);
						var row = viewModel.KnowledgeTypeVodata.getSelectedRows()[0];
						
						if(row){
							var parentId = row.getValue('pk_exam_knowledge_type');
							var parentName = row.getValue('knowlege_type_name');
						}
						
						var newr = viewModel.KnowledgeTypeVodatanew.createEmptyRow();
			            viewModel.KnowledgeTypeVodatanew.setRowSelect(newr);
			            
			            if(row){
			            	var newrow = viewModel.KnowledgeTypeVodatanew.getSelectedRows()[0];
			            	newrow.setValue('pk_knowledge_type_parentid',parentId);
			            	newrow.setValue('pk_knowledge_type_parentid_name',parentName);
			            }

			            window.md = u.dialog({id:'add_depart',content:"#dialog_content_instit",hasCloseMenu:true});
					},
					editinstitClick:function(){
						$('#dialog_content_instit').find('.u-msg-title').html("<h4>编辑机构</h4>");
						viewModel.event.clearDt(viewModel.KnowledgeTypeVodatanew);
						var row = viewModel.KnowledgeTypeVodata.getSelectedRows()[0];
						if(row){
							if(row.data.pk_knowledge_type_parentid.value){
								row.setValue('pk_knowledge_type_parentid_name',$("#tree2")[0]['u-meta'].tree.getNodeByParam('id',row.getValue('pk_knowledge_type_parentid')).name);
							}
							viewModel.KnowledgeTypeVodatanew.setSimpleData(viewModel.KnowledgeTypeVodata.getSimpleData({type: 'select'}));
							$('#knowlege_type_code').attr("readonly","readonly");
							$('#pk_knowledge_type_parentid_name').attr("readonly","readonly");
							window.md = u.dialog({id:'edit_depart',content:"#dialog_content_instit",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delinstitClick:function(){
						var row = viewModel.KnowledgeTypeVodata.getSelectedRows()[0]
						if(row){
							var data = viewModel.KnowledgeTypeVodata.getSelectedRows()[0].getSimpleData()
							u.confirmDialog({
					            msg: "是否删除"+data.knowlege_type_name+"?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.deleteTree(data);
					                viewModel.KnowledgeTypeVodata.removeRow(viewModel.KnowledgeTypeVodata.getSelectedIndexs());
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					saveinstitClick:function(){
						var data = viewModel.KnowledgeTypeVodatanew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#dialog_content_instit')[0])) {
	                        return;
	                    }
                        viewModel.event.saveTree(data);
					},
					cancelinstitClick:function(){
							md.close();
					},
					addManClick:function(){
						$('#dialog_content_man').find('.u-msg-title').html("<h4>新增试卷</h4>");
						viewModel.event.clearDt(viewModel.PaperMainVodatanew);
						var row = viewModel.KnowledgeTypeVodata.getSelectedRows()[0];
						if(row){
							var institId = row.getValue('pk_exam_knowledge_type');
							var instit = row.getValue('knowlege_type_name');
							var newr = viewModel.PaperMainVodatanew.createEmptyRow();
							viewModel.PaperMainVodatanew.setRowSelect(newr);
							var newrow = viewModel.PaperMainVodatanew.getSelectedRows()[0];
							newrow.setValue('fk_id_papermainvo',institId);
							newrow.setValue('using_status', "1");   //设置是否启用默认值
							newrow.setValue('checkanswer_status', "1");   //设置是否查看答案默认值
							newrow.setValue('checkscore_status', "1");   //设置是否查看分数默认值
							window.md = u.dialog({id:'add_man',content:"#dialog_content_man",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择分类！',title:'操作提示',btnText:'确定'});
						}
					},
					editManClick:function(){
						$('#dialog_content_man').find('.u-msg-title').html("<h4>编辑试卷</h4>");
						viewModel.event.clearDt(viewModel.PaperMainVodatanew);
						var row = viewModel.PaperMainVodata.getSelectedRows()[0]
						if(row){
							viewModel.PaperMainVodatanew.setSimpleData(viewModel.PaperMainVodata.getSimpleData({type: 'select'}));
							window.md = u.dialog({id:'edit_man',content:"#dialog_content_man",hasCloseMenu:true});
						}else{
							u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					delManClick:function(){
						var row = viewModel.PaperMainVodata.getSelectedRows()[0];
						if(row){
							var data = viewModel.PaperMainVodata.getSelectedRows()[0].getSimpleData()
							u.confirmDialog({
					            msg: "是否删除"+data.paper_name+"?",
					            title: "删除确认",
					            onOk: function () {
					                viewModel.event.delMan(data);
					                viewModel.PaperMainVodata.removeRow(viewModel.PaperMainVodata.getSelectedIndexs());
					            },
					            onCancel: function () {
					            }
							});
						}else{
							u.messageDialog({msg:'请选择要删除的数据！',title:'操作提示',btnText:'确定'});
						}
					},
					saveManClick:function(){
						var data = viewModel.PaperMainVodatanew.getSelectedRows()[0].getSimpleData();
	                    if (!viewModel.app.compsValidate($('#add-form')[0])) {
	                        return;
	                    }
                        viewModel.event.saveMan(data);
					},
					cancelManClick:function(){
							md.close();
					},
					searchClick:function(){
						viewModel.draw = 1; 
						viewModel.event.loadTable(treeid);
					},
					//关闭上传对话框
					canceupload:function(){
						$("#file-list div.c-cart-table-row").remove();
						pluploadmd.close();
					},
					//导入试卷
					uploadManClick:function(){
						var row = viewModel.KnowledgeTypeVodata.getSelectedRows()[0];
						if(row){
							var row = viewModel.PaperMainVodata.getSelectedRows()[0]
							if(row){
								$('#upload_files').html("");
								window.pluploadmd = u.dialog({id:'upload_depart',content:"#dialog_content_upload",hasCloseMenu:true});
							}else{
								u.messageDialog({msg:'请选择要编辑的数据！',title:'操作提示',btnText:'确定'});
							}
						}else{
							u.messageDialog({msg:'请选择分类！',title:'操作提示',btnText:'确定'});
						}			
					},

				/**
                 * 关闭弹出框
                 */
                mdClose: function () {
                    refmd.close();
                },
              

                /**绑定弹出层 树的按钮 */
                bindClickButton: function (ele, data, functionevent) { //对某一个按钮进行  点击事假绑定 ele:被绑定的元素，  data：需要传递的数据，functionevent：绑定的方法
                    $(ele).unbind('click'); //取消之前的绑定
                    $(ele).bind('click', data, functionevent); //重新绑定
                },
                            					
			},
//			 //上传文件
//				fileupload:function(){
//					viewModel.uploader=new plupload.Uploader({ //实例化一个plupload上传对象
//						//上传插件初始化选用那种方式的优先级顺序，如果第一个初始化失败就走第二个，依次类推
//						runtimes: 'html5,flash,silverlight,html4',
//						browse_button : 'browse',
//						url : uploadUrl,
//						flash_swf_url : 'vendor/plupload/Moxie.swf',
//						silverlight_xap_url : 'vendor/plupload/Moxie.xap',
//						//配置上传相关参数
////						filters: {
////							max_file_size: '1000mb',
////							mime_types: [{
////								title: "file",
////								extensions: "txt,mp4,jpg,jpeg,gif,png"
////							}]
////						},
////						
////						//多选文件上传
////						multi_selection: false,
//						
//						//初始化
//						init: {
//							//上传前配置参数,该参数用于传达到后台
//							BeforeUpload: function(up, files) {
//								viewModel.uploader.setOption("multipart_params",{
//									"pk_exam_knowledge_type": viewModel.KnowledgeTypeVodata.getSelectedRows()[0].getValue('pk_exam_knowledge_type'),
//									"pk_exam_paper_main":viewModel.PaperMainVodata.getSelectedRows()[0].getValue('pk_exam_paper_main'),
//								});
//							},
//							//上传开始
//							FilesAdded: function(up, files) {
//								for(var i = 0, len = files.length; i<len; i++){
//									var file_name = files[i].name; //文件名
//									//构造html来更新UI
//									var html= '<tr id="' + files[i].id +'">'+
//													'<td>' + file_name + '</td>'+
//													'<td><div class="progress" style="height:25px;"><div  id="file-' + files[i].id +'" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;background-color: #ff7800"> 0%</div></div></td>'+
//													'<td><button class="btn btn-xs green" type="button"  onclick="removefile(\''+files[i].id+'\')" >删除</button></td>'+
//											  '</tr>';
//									$(html).appendTo('#upload_files');
//								}
//								
//								viewModel.uploader.start(); //开始上传
//							},
//							//上传中   绑定文件上传进度事件 UploadProgress
//							UploadProgress: function(up, file) {
//								$('#file-'+file.id).css('width',file.percent + '%');//控制进度条
//								$('#file-'+file.id).attr('aria-valuenow',file.percent);
//								$('#file-'+file.id).text(file.percent + '%');
//							},
//							
//							//上传出错
//							Error: function(up, err) {
//								u.messageDialog({msg:'上传出错！',title:'操作提示',btnText:'确定'});
//								return;
//							},
//							//上传完成
//							UploadComplete: function(up, file) {
//								$('#file-'+file[file.length-1].id).css('width',file[file.length-1].percent + '%');//控制进度条
//								$('#file-'+file[file.length-1].id).attr('aria-valuenow',file[file.length-1].percent);
//								$('#file-'+file[file.length-1].id).text(file[file.length-1].percent + '%');
//								
//								//回写shang传路径
//								//var url='doc'+'\\'+viewModel.KnowledgeTypeVodata.getSelectedRows()[0].getValue('pk_exam_knowledge_type')+'\\'+file[file.length-1].id+'\\'+file[file.length-1].name;
//								//var newrow = viewModel.PaperMainVodatanew.getSelectedRows()[0];
////								viewModel.flag?newrow.setValue('courseUrl',url):newrow.setValue('courseIconUrl',url);
//								//newrow.setValue('',url);
//							}
//						}
//					});
//					viewModel.uploader.init();
//					//上传按钮
//					$('#upload-btn').click(function(){
//						//viewModel.uploader.start(); //开始上传
//						viewModel.uploader.init();
//					});
//				}		
			//上传文件
			fileupload:function(){
				viewModel.uploader=new plupload.Uploader({ //实例化一个plupload上传对象
					//上传插件初始化选用那种方式的优先级顺序，如果第一个初始化失败就走第二个，依次类推
					runtimes: 'html5,flash,silverlight,html4',
					browse_button : 'browse',
					url : uploadUrl,
					flash_swf_url : 'vendor/plupload/Moxie.swf',
					silverlight_xap_url : 'vendor/plupload/Moxie.xap',
				});
				//初始化
				viewModel.uploader.init();
				//上传前配置参数,该参数用于传达到后台
				viewModel.uploader.bind('BeforeUpload',function(uploader,files){
					viewModel.uploader.setOption("multipart_params",{
						"pk_exam_knowledge_type": viewModel.KnowledgeTypeVodata.getSelectedRows()[0].getValue('pk_exam_knowledge_type'),
						"pk_exam_paper_main":viewModel.PaperMainVodata.getSelectedRows()[0].getValue('pk_exam_paper_main'),
					});
				}),
				//绑定文件添加进队列事件
				viewModel.uploader.bind('FilesAdded',function(uploader,files){
					for(var i = 0, len = files.length; i<len; i++){
						var file_name = files[i].name; //文件名
						//构造html来更新UI
						var html= '<tr id="' + files[i].id +'">'+
										'<td>' + file_name + '</td>'+
										'<td><div class="progress" style="height:25px;"><div  id="file-' + files[i].id +'" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;background-color: #ff7800"> 0%</div></div></td>'+
										'<td><button class="btn btn-xs green" type="button" onclick=\"removefile(\''+files[i].id+'\',this)\">删除</button></td>'+
								  '</tr>';
						$(html).appendTo('#upload_files');
					}
				});
				//绑定文件显示上传进度
				viewModel.uploader.bind('UploadProgress',function(uploader,file){
					$('#file-'+file.id).css('width',file.percent + '%');//控制进度条
					$('#file-'+file.id).attr('aria-valuenow',file.percent);
					$('#file-'+file.id).text(file.percent + '%');
				});
				//服务器返回的上传信息
				viewModel.uploader.bind('FileUploaded',function(uploader,file,responseObject){
					if(200 == responseObject.status){
						u.messageDialog({msg:'上传成功！',title:'操作提示',btnText:'确定'});
						pluploadmd.close();
					}else{
						u.messageDialog({msg:'上传出错！',title:'操作提示',btnText:'确定'});
					}
				})
				//上传按钮
				$('#upload-btn').click(function(){
					viewModel.uploader.start(); //开始上传
				});
			}
			//上传文件  end			
					
		};
		//viewModel end
        //是否启用	
		viewModel.PaperMainVodatanew.on("using_statuses.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#combousing')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.PaperMainVodatanew.setValue('using_status', datas[i].name);
                    break;
                }
            }
        });
		//是否查看答案
		viewModel.PaperMainVodatanew.on("checkanswer_statuses.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#combocheckanswer')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.PaperMainVodatanew.setValue('checkanswer_status', datas[i].name);
                    break;
                }
            }
        });
		//是否查看分数
		viewModel.PaperMainVodatanew.on("checkscore_statuses.valueChange", function (event) {
            var comboId = event.newValue;
            var datas = $(element).find('#combocheckscore')[0]['u.Combo'].comboDatas;
            for (var i = 0; i < datas.length; i++) {
                if (comboId == datas[i].value) {
                    viewModel.PaperMainVodatanew.setValue('checkscore_status', datas[i].name);
                    break;
                }
            }
        });
		
		
		$(element).html(html) ;
		viewModel.event.pageInit();
        viewModel.fileupload();
		
		/*兼容ie日期中后退按钮bug*/
        if(u.isIE){
	        $('#upload_date').keydown(function(e){
	    		e.preventDefault();
	    	})
	    	 $('#begin_time').keydown(function(e){
	    		e.preventDefault();
	    	})
	    	 $('#end_time').keydown(function(e){
	    		e.preventDefault();
	    	})
        }
	}
	
	return {
		'template': html,
        'init':init
	}
});
