// index.js
// 获取应用实例
const app = getApp()
Page({
  data: {
    inputVal: '',
    result: ''
  },
  bindKeyInput: function(e){
    this.setData({
      inputVal: e.detail.value
    })
  },
  enter: function(e){
    var that = this;
    wx.request({
      url: 'http://3a40r96515.zicp.vip/EasySend/easyCopy?msg=' + that.data.inputVal,
      header: {
        "Content-Type": "application/json"
      },
      success: function (res) {
        var t = res.data;
        if(t == 200){
          that.setData({
            result: 'Successful !!!'
          })
        }else {
          that.setData({
            result: 'Failed !!!'
          })
        }
      }
    })
  }
})
