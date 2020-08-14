module.exports = {
  root: true,
  parserOptions: {
    //此项是用来指定javaScript语言类型和风格
    parser: 'babel-eslint'
  },
  env: {
    //此项指定环境的全局变量，下面的配置指定为浏览器环境
    browser: true,
  },
  //此项是用来配置标准的js风格，就是说写代码的时候要规范的写，如果你使用vs-code我觉得应该可以避免出错
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential',
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  // 此项是用来提供插件的，插件名称省略了eslint-plugin-，下面这个配置是用来规范vue的
  plugins: [
    'vue'
  ],
  /*add your custom rules here
  // 下面这些rules是用来设置从插件来的规范代码的规则，使用必须去掉前缀eslint-plugin-
  // 主要有如下的设置规则，可以设置字符串也可以设置数字，两者效果一致
    // "off" -> 0 关闭规则
    // "warn" -> 1 开启警告规则
    //"error" -> 2 开启错误规则
  */
  rules: {
    //空行最多不能超过2行
    'no-multiple-empty-lines': 0,
    'no-tabs': 'off',
    // allow async-await
    //生成器函数*的前后空格
    'generator-star-spacing': 'off',
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 1,
    // 函数定义时括号前面要不要有空格
    "space-before-function-paren": ["off", "always"],
    'indent': 'off',        ////缩进风格
    'vue/script-indent': ['warn', 2, {
      'baseIndent': 1
    }]
  }
}
