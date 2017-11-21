var today = new Date();
var todayStr = today.getFullYear() + '/' + (today.getMonth() + 1) + '/' + today.getDate();
var metaTree = {
        meta: {
                    pk_exam_knowledge_type:{
                    	type: 'string'
                    },  
                    pk_space:{
                    	type: 'string'
                    },  
                    knowlege_type_code:{
                    	type: 'string'
                    },  
                    knowlege_type_name:{
                    	type: 'string'
                    },  
                    pk_knowledge_type_parentid:{
                    	type: 'string'
                    },  
					pk_knowledge_type_parentid_name:{
						type: 'string'
                    },
                    id_papermainvo:{
                    	type: 'string'
                    },  
		}
	};
	var metaPaperMainVo = {
		 meta: {
		 			fk_id_papermainvo:{
		 			
		 			},
                    pk_exam_paper_main:{
                    
                    },    
                    pk_exam_knowledge_type:{
                    
                    },    
                    pk_space:{
                    
                    },    
                    paper_code:{
                    
                    },    
                    paper_name:{
                    
                    },    
                    begin_time:{
                    	type: 'date',
          			  'default': todayStr
                    },    
                    end_time:{
                    	type: 'date',
          			  'default': todayStr
                    },    
                    exam_time:{
                    
                    }, 
                    total_score:{
                        
                    },
                    pass_score:{
                    
                    },    
                    using_status:{
                      type: 'string'
                    },    
                    examnum:{
                    
                    },    
                    uploader:{
                    
                    },    
                    upload_date:{
                      type: 'date',
        			  'default': todayStr
                    },    
                    checkanswer_status:{
                    
                    },    
                    checkscore_status:{
                    
                    },    
		}
	}; 
//end userjob meta
