// 获取元素引用  
var searchInput = document.getElementById('searchInput');  
var suggestionsDiv = document.getElementById('suggestions');  
var searchBnt = document.getElementById('searchBnt');  
  
// 假设的搜索建议数据  
var suggestions = ['计算机', '数学', '物理', '艺术', '小说'];  
  
// 监听输入框的输入事件  
searchInput.addEventListener('input', function(e) {  
    var value = e.target.value;  
    if (value.trim() === '') {  
        // 如果输入框为空，则隐藏搜索建议  
        suggestionsDiv.style.display = 'none';  
    } else {  
        // 否则显示搜索建议，并过滤出匹配的建议  
        var filteredSuggestions = suggestions.filter(function(suggestion) {  
            return suggestion.toLowerCase().indexOf(value.toLowerCase()) !== -1;  
        });  
          
        // 清空当前建议并添加新建议  
        suggestionsDiv.innerHTML = '';  
        filteredSuggestions.forEach(function(suggestion) {  
            var li = document.createElement('li');  
            li.textContent = suggestion;  
            li.addEventListener('click', function() {  
                // 当点击建议时，将建议值设置到输入框中，并隐藏建议框  
                searchInput.value = suggestion;  
                suggestionsDiv.style.display = 'none';  
            });  
            suggestionsDiv.appendChild(li);  
        });  
          
        // 显示搜索建议框  
        suggestionsDiv.style.display = 'block';  
    }  
});  
  
// 如果需要，可以为搜索按钮添加点击事件处理函数  
// searchBnt.addEventListener('click', function() {  
//     // 执行搜索操作
//     console.log("搜索");
//     window.open("/common/searchBook", "_blank");
// });