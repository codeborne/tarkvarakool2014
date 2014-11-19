<@html>
  <#if goal?has_content>
  <input type="hidden" value="${goal.id?c}" name="goalId">
    <div class="panel panel-default">
      <div class="goal">
        <div class="panel-heading">
          <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
          <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
          <h4 class="budget"><@m'budget'/> ${goal.budget?c} €</h4>
        </div>
      </div>
      <div class="panel-body">
      <div class="chart" style= "height: 600px;"></div>
</div>
    </div>

  </#if>

<script>
  google.load("visualization", "1", {packages:["corechart"],language:'et'});
  google.setOnLoadCallback(drawChart);
  function drawChart() {
    var jsonData = $.ajax({
      url: "/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {goalId: $("input").val()}
    }).responseText;
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));
  console.log(jsonData);
  console.log(data1);
    var data = google.visualization.arrayToDataTable(data1);
    var options = {
//      curveType: 'none',
//      pointSize: 5,
//      hAxis: {gridlines: {count: 8}},
      hAxis:{format:'####'},
      vAxis:{format:'#%'},
//      width: 1000,
      legend: { position: 'right'}
    };
    var chart =  new google.visualization.ColumnChart(document.getElementById('chart'));




    chart.draw(data, options);
  }
</script>
</@html>
