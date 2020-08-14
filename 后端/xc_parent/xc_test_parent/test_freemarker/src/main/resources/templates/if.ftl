<#--if判断-->
<#--<#if token==1234>
    token 是1234<br/>
</#if>

<#if token2!="1234">
    token2不是5678 <br/>
</#if>-->

<#--不要使用> 用gt 替换-->
<#--
<#if token gt 100>
    大于 <br/>
</#if>
-->


<#--空值处理-->
<#--不为空处理-->
<#if token??>
    token不为空 <br/>
</#if>

<#--为空处理-->
<#if !token3??>
    token3为空 <br/>
</#if>

<#--默认值-->
${token3!'3的默认值'}<br/>

