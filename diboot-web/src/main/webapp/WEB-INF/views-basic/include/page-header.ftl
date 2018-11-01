<header class="main-header">
<#-- Logo -->
    <a href="${ctx.contextPath}/welcome" class="logo">
    <#-- logo for regular state and mobile devices -->
        Diboot <small>轻代码开发平台</small>
    </a>
<#-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
    <#-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>
    <#-- BEGIN TOP NAVIGATION MENU -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
            <#-- BEGIN NOTIFICATION DROPDOWN
                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-warning" title="新的消息通知">1</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <ul class="menu">
                                <li>
                                    <a href="#">
                                        <i class="fa fa-bullhorn"></i> 新系统上线通知！
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">查看更多</a></li>
                    </ul>
                </li>-->
                <#-- BEGIN USER LOGIN DROPDOWN -->
                <li class="dropdown notifications-menu user user-menu">
                    <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <img alt="" class="user-image" src="${ctx.contextPath}/static/img/avatar.jpg" />
                        <span class="hidden-xs"> ${(Session.user.realname)!"User"} </span>
                        <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu" style="width: auto;">
                        <li class="header">
                            <a href="${ctx.contextPath}/user/changepwd">
                                <i class="fa fa-lock"></i> 修改密码 </a>
                        </li>
                        <li class="header">
                            <a href="${ctx.contextPath}/logout">
                                <i class="fa fa-sign-out"></i> 退出登录 </a>
                        </li>
                    </ul>
                </li>
            <#-- END USER LOGIN DROPDOWN -->
            <#-- BEGIN LOGOUT -->
                <li class="dropdown dropdown-quick-sidebar-toggler">
                    <a href="${ctx.contextPath}/logout" class="dropdown-toggle" title="退出登录">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </li>
            <#-- END LOGOUT -->
            </ul>
        </div>
    <#-- END TOP NAVIGATION MENU -->
    </nav>
</header>