var ioc = {
	// 一个所有 API 的父配置对象
	"api" : {
		fields : {
			dao : {java:'$conf.dao($connector, "db-name")'} 
		}
	},
	// 初始化服务
	"init" : {
		type : 'org.nutz.ztask.impl.mongo.ZTaskMongoInit',
		parent : 'api'
	}
// 结束所有的 API 配置
};