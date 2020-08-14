显示集合大小: ${list?size} <br/>

显示年月日:${birthday?date} <br/>
显示时分秒: ${birthday?time} <br/>
显示日期+时间: ${birthday?datetime} <br/>
自定义格式化: ${birthday?string("yyyy年MM月")}<br/>
${birthday?string("yyyy/MM/dd HH:mm:ss:SSS")}<br/>

整形数据:<br/>
默认显示:${num} <br/>
格式化:${num?c} <br/>

JSON数据:<br/>
字符串:${userJSON} <br/>
<#assign user=userJSON?eval />
对象输出:${user.username} <br/>