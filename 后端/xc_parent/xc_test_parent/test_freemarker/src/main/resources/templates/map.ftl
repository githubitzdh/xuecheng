<#--指定key -->
${allUser.user1.username}<br/>
${allUser['user1'].username}<br/>

<#--遍历一组-->
<#list allUser?keys as k>
    ${k} --${allUser[k].username} --${allUser[k].password} --${allUser[k].age} <br/>
</#list>
