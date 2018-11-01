<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="修改密码" loc1url="${ctx.contextPath}/user/changepwd" loc2="修改密码"/>
	<div class="content">
		<@portlet>
			<@portletTitle title="修改密码">
				<div class="tools">
					<a href="javascript:;" class="collapse"> </a>
				</div>
			</@portletTitle>
			<@portletBody>
				<p class="text-center text-warning"><strong>提示:</strong>更新密码后，请牢记新密码！</p>
				<form class="form-horizontal" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="oldpassword">旧密码</label>
						<div class="col-sm-9">
							<input type="password" id="oldPassword" name="oldPassword" class="form-control" placeholder="目前的登录密码"
								   required="true" data-fv="NotNull,Length(-16)">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="newpassword">新密码</label>
						<div class="col-sm-9">
							<input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="设定新的登录密码（6-16位）"
								   required="true" data-fv="NotNull,Length(-16)">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="newpassword2">确认新密码</label>
						<div class="col-sm-9">
							<input type="password" id="newPassword2" name="newPassword2" class="form-control" placeholder="重复输入新密码"
								   required="true" data-fv="NotNull,Length(-16)">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-2">
							<button class="btn btn-primary btn-block" type="submit"> 提交 </button>
						</div>
					</div>
				</form>
			</@portletBody>
		</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>