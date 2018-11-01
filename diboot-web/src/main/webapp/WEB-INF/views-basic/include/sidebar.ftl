<#-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
<#-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
    <#-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${ctx.contextPath}${(Session.user.avatar)!''}" class="img-circle" alt="${(Session.user.username)!"头像"}">
            </div>
            <div class="pull-left info">
                <p>${(Session.user.realname)!""}</p>
                <small>${(Session.user.username)!""}</small>
            </div>
        </div>
        <#-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu tree" data-widget="tree">
            <li data-m="welcome" class="home nav-item">
                <a href="${ctx.contextPath}/welcome">
                    <i class="fa fa-home"></i>
                    <span> 我的工作台</span>
                    <span class="pull-right-container">
              <span class="label label-primary pull-right"></span>
            </span>
                </a>
            </li>
        <#if (Session.user.menus)?exists>
            <#list Session.user.menus as menu>
                <#if menu.link?? && menu.link != ''>
                    <li class="nav-item" data-m="${(menu.link)!''}">
                        <a href="${ctx.contextPath}${menu.link}">
                            <i class="${(menu.icon)!""}"></i> <span>${menu.name}</span>
                        </a>
                    </li>
                <#else>
                    <li data-m="${(menu.link)!''}" class="nav-item treeview">
                        <a href="#">
                            <i class="${menu.icon}"></i>
                            <span>${menu.name}</span>
                            <span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
                        </a>
                        <#if menu.children??>
                            <ul class="treeview-menu">
                                <#list menu.children as c>
                                    <li class="nav-item" data-m="${(c.link)!''}">
                                        <a href="${ctx.contextPath}${c.link}">
                                            <i class="${(c.icon)!'fa fa-circle-o'}"></i> ${c.name}
                                        </a>
                                    </li>
                                </#list>
                            </ul>
                        </#if>
                    </li>
                </#if>
            </#list>
        </#if>
        </ul>
    </section>
<#-- /.sidebar -->
</aside>