var metaCourseType = {
				meta : {
					courseTypeCode:{
						type:'string',
						required:true,
						notipFlag: true,
						hasSuccess: true,
						nullMsg:'课程分类编码不能为空!'
					},
					courseTypeName:{
						type:'string',
						required:true,
						notipFlag: true,
						hasSuccess: true,
						nullMsg:'课程分类名称不能为空!'
					},
					pkCourseType: {
						 type: 'string'
					},
					pkCourseTypeParentid : {
						 type: 'string'
					},
					pkCourseTypeParentName : {
						 type: 'string'
					}
				}
			};
		
var metaCourse = {
		meta:{
			pkCourse: {
                type: 'string'
            },
            courseName: {
                type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'课程名不能为空!'
            },
            courseCode: {
                type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'课程编号不能为空!'
            },
            courseBroadcastNumber: {
                type: 'string'
            },
            courseClickNumber:{
            	 type: 'string'
            },
            courseDuraion: {
                type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'课程时长不能为空!'
            },
            courseIconUrl: {
                type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'请上传课程图标！'
            },
            chapterUrl:{
            	type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'请上传课程章节!'
            },
            courseIntroduction: {
            	type: 'string'
            },
            courseLecturer: {
            	type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'课程讲师不能为空!'
            },
            courseLikeNumber: {
            	type: 'string'
            },
            coursePublisher: {
            	type: 'string'
            },
            courseSection: {
            	type: 'string'
            },
            courseUrl: {
            	type: 'string',
				required:true,
				notipFlag: true,
				hasSuccess: true,
				nullMsg:'请上传课程视频!'
            },
            pkCourseType: {
            	type: 'string'
            },
            isuse: {
            	type: 'string'
            },
            dr: {
            	type: 'string'
            },
            coursePublishTime: {
            	type: 'string'
            },
            ts: {
            	type: 'string'
            }
		}
	};