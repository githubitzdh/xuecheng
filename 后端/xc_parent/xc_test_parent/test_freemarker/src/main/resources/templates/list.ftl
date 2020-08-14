list遍历
<#list  allUser as user>
    ${user_index +1} -- ${user.username} --${user.password}--${user.age}<br/>
</#list>


<hr/>
table遍历展示

<table border="1">
    <tr>
        <td>用户名</td>
        <td>密码</td>
        <td>年龄</td>
    </tr>
    <#list allUser as user>
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.age}</td>
        </tr>
    </#list>
</table>

