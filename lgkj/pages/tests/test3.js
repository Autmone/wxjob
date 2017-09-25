var texts= [
      '1、你是一个固执的人吗？','2、你是一个不论什么事情都喜欢据理力争的人吗？','3、觉得委曲时，你通常会怎么做？','4、你是一个容易被感动的人吗？','5、心情不好时，你会将情绪表现出来吗？','6、你是一个有主见的人吗？','7、你会选择怎样度过周末？','8、你是一个喜欢交际应酬的人吗？','9、你与别人的相处融洽吗？','10、你是一个能按时完成工作的人吗？'
    ];
var items= [
        [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '偶尔是 ', value: '0'}],
        [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '偶尔是 ', value: '0'}],
        [{name: '自己一个人独处 ', value: '0'},{name: '找人倾诉 ', value: '0'},{name: '不知所措 ', value: '0'}],
        [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '看情况而定 ', value: '0'}],
        [{name: '会 ', value: '0'},{name: '不会 ', value: '0'},{name: '偶尔会 ', value: '0'}],
        [{name: '是 ', value: '0'},{name: '不是 ', value: '0'},{name: '偶尔是 ', value: '0'}],
        [{name: '出门逛街 ', value: '0'},{name: '一个人待着 ', value: '0'},{name: '打网游 ', value: '0'}],
        [{name: '是 ', value: '4'},{name: '不是 ', value: '0'},{name: '看情况而定 ', value: '0'}],
        [{name: '融洽 ', value: '2'},{name: '不融洽 ', value: '1'},{name: '偶尔融洽 ', value: '0'}],
        [{name: '完全没问题 ', value: '3'},{name: '经常完不成 ', value: '1'},{name: '不一定 ', value: '2'}]
    ];
    var results = [
        "A、特别硬.你就是传说中才会出现的嘴特别硬的人，无论什么时候，你都不会在嘴上吃亏松口，即使心中明知情况并不乐观，但是输人不能输阵，所以嘴上工夫自然不能白练，无论何时何地，都会保持一贯的坚硬品质死磕到底，其实做人，真的不需要这么累的。",
        "B、一般硬.如果一件事情你有理有据，那么你的嘴就会硬得非常有底气，但如果一旦发现理亏的话，你的气焰马上就会有些萎靡，嘴上自然也就不那么硬气。其实，这样的性格，真的很可爱，该硬的时候硬，知错又能改，也难怪你虽然有时嘴硬得让人生气，却从不会让人讨厌。",
        "C、有点儿硬.你的嘴只能算是有点儿硬，如果对方恰好比较好说话的话，那么你的嘴硬还能获得点儿成就感，而如果对方恰好不那么好说话，你的嘴马上就会变得不那么硬，正因如此，所以你的嘴硬，更多的只是一种流于表面的假象，充其量也就是因人而异。",
        "D、硬不起来.嘴硬这件事，跟你其实是没有什么关系的。因为你的嘴不仅不硬，甚至还有些软呢，但是这跟耳根子软的概念可不一样，你只是说话更温和，更擅于说别人喜欢听的话，也因此，让你看起来更招人喜欢，因为你从来都不是一个死磕到底不知悔改的人。"
    ];
    var index=0;

Page({
  data: {
    text:texts[0],
    item:items[0],
    result:""
  },

  radioChange: function(e) {
        if(e.detail.value == 0) {
            index++;
            this.setData({
                text:texts[index],
                item:items[index]
            })
        } else if(e.detail.value == 1) {
            this.setData({
                result:results[0]
            })
        } else if(e.detail.value == 2) {
            this.setData({
                result:results[1]
            })
        } else if(e.detail.value == 3) {
            this.setData({
                result:results[2]
            })
        } else if(e.detail.value == 4) {
            this.setData({
                result:results[3]
            })
        }
  }
})