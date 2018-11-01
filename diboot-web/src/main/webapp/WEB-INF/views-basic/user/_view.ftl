<div class="row">
    <div class="col-md-3">
        <div class="box box-default">
            <div class="box-body box-profile">
                    <img src="${(model.avatar)!''}" class="profile-user-img img-responsive img-circle" alt="">
                    <div class="profile-username text-center"> ${(model.realname)!""} </div>
                    <div class="text-muted text-center"> ${(model.roleNames)!""} </div>
            </div>
        </div>
    </div>

    <#-- 右侧内容 -->
    <div class="col-md-9">
        <div class="box box-default">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <tbody>
                    <tr>
                        <td class="td-label">姓名</td>
                        <td>${(model.realname)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">角色</td>
                        <td>${(model.roleNames)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">用户名</td>
                        <td>${(model.username)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">手机号码</td>
                        <td>${(model.phone)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">微信号</td>
                        <td>${(model.wechat)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">Email</td>
                        <td>${(model.email)!"-"}</td>
                    </tr>
                    <tr>
                        <td class="td-label">账号状态</td>
                        <td>${model.enabled?string('正常','锁定')}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>