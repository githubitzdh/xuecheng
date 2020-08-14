'use strict'
const express = require('express')
const chalk = require('chalk')
const app = express()

// 设置访问资源目录
app.use(express.static('dist'))

// 启动一个程序
// 语法： app.listen( 端口号, 成功回调函数 )
app.listen(4005, () => {
  console.log('server start at: ' + chalk.blue(`http://localhost:4005`))
})
