<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc2="我的工作台" />
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <h3 class="page-title"> ${AP}好，${(Session.user.realname)!""} : )</h3>
            </div>
        </div>
    </section>
</@wrapper>
</body>
</html>