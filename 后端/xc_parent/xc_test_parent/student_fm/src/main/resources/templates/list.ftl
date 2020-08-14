 <form action="/student/add" method="post">
        用户名:<input type="text" name="name"/> <br/>
        密码:<input type="text" name="password"/> <br/>
        学历:<input type="text" name="edu"/><br>
        介绍:<input type="text" name="description"/><br/>
        <input type="submit" value="添加">
    </form>

<h1>---</h1>
<table width="70%" border="1">
    <tr>
        <td>id</td>
        <td>姓名</td>
        <td>密码</td>
        <td>学历</td>
        <td>介绍</td>
    </tr>
    <#list slist as s>
        <tr>
            <td>${s.sid}</td>
            <td>${s.name}</td>
            <td>${s.password}</td>
            <td>${s.edu}</td>
            <td>${s.description}</td>

            <td>
                <a href="/student/findById?sid=${s.sid}">修改</a>
                <a href="/student/del?sid=${s.sid}">删除</a>
            </td>
        </tr>
    </#list>

</table>
