<#list slist as s>
    <h1>修改</h1>
    <form action="/student/ed" method="post">
        <input type="hidden" name="sid" value="${s.sid}"/>
        用户名:<input type="text" value="${s.name}" name="name"/><br/>
        密码:<input type="text" value="${s.password}" name="password"/><br/>
        学历:<input type="text" value="${s.edu}" name="edu"/><br>
        介绍:<input type="text" value="${s.description}" name="description"/><br>
        <input type="submit" value="提交">
    </form>

</#list>