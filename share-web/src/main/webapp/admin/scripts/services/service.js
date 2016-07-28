angular.module('shareApp_admin').factory("userService",function($resource){
	return $resource('user/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("typeService",function($resource){
	return $resource('type/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("stuffService",function($resource){
	return $resource('stuff/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("roleService",function($resource){
	return $resource('role/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("replyService",function($resource){
	return $resource('reply/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("messageService",function($resource){
	return $resource('message/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("commentService",function($resource){
	return $resource('comment/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("attachmentService",function($resource){
	return $resource('attachment/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("exchangeStuffService",function($resource){
	return $resource('exchangeStuff/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("msgCountService",function($resource){
	return $resource('msgCount/:id.json',
			{id:"@id"},
			{
        'query' : {
            method : 'GET'
        },
        'get' : {
            method : 'GET'
        },
        'add' : {
            method : 'POST'
        },
        'update' : {
            method : 'PUT'
        },
        'delete' : {
            method : 'DELETE'
        }
});
}).factory("alertService",function($alert){
	var alertService={};
	alertService.show=function(msg,type,container,delay,position,title){
		msg = msg ? msg : "";
		type = type ? type : "danger";
		title = title ? title : "";
		container = container ? container : "#alert_div";
		position = position ? position : "top";
		delay = delay ? delay : 2;
		var Alert = $alert({title: title, content: msg, placement: position, type: type,container:container, duration:delay,animation:"am-fade-and-slide-top", show: true});
	};
	return alertService;
});