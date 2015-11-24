var MyValidator = function() {
    var handleSubmit = function() {
        $('.form-horizontal').validate({
            errorElement : 'span',
            errorClass : 'help-block',
            focusInvalid : false,
            rules : {
                name : {
                    required : true
                },
                password : {
                    required : true
                },
                intro : {
                    required : true
                },gender:{
                	required : true
                },hobby:{
                	required : true
                },sel:{
                	required : true
                }
            },messages : {
				name : {
					required : "Username is required."
				},
				password : {
					required : "Password is required."
				},
				intro : {
					required : "Intro is required."
				},
				gender : {
					required : "性别必填 ."
				},
				hobby : {
					required : "爱好必填"
				},
				sel : {
					required : "爱好必填"
				}
			},

            highlight : function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },

            success : function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement : function(error, element) {
                element.parent('div').append(error);
            },

            submitHandler : function(form) {
                form.submit();
            }
        });

        $('.form-horizontal input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.form-horizontal').validate().form()) {
                    $('.form-horizontal').submit();
                }
                return false;
            }
        });
    }
    return {
        init : function() {
            handleSubmit();
        }
    };

}();
