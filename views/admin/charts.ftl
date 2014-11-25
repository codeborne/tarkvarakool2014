<@html>
  <#if goal?has_content>
  <input type="hidden" value="${goal.id?c}" name="goalId">
  <div class="panel panel-default">
    <div class="goal">
      <div class="panel-heading panel-heading-chart">
        <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
        <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
        <h4 class="budget"><@m'budget'/> ${goal.budget?c} €</h4>
      </div>
    </div>
    <div class="panel-body" id="chart">
    </div>
    <div class="chartLegend" style="background-color: white; padding-left: 20px;">
      <table class="legendTable">
        <#list metricsWithValidLevels as metric>
          <#list graphColors as color>
            <#if metric_index == color_index>
              <tr>
                <td><div class="legendRow" id="legendBox_${color_index}" style="background-color: ${color};"></div></td>
                <td><span class="legendMetricName"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></span></td>
              </tr>

            </#if>
            <#if (!metric_has_next && ((metric_index+1) == color_index))>
              <tr>
                <td><div class="legendRow" id="legendBox_${color_index}" style="background-color: ${color};"></div></td>
                <td><span class="legendMetricName"><@m'budgetLegend'/></span></td>
              </tr>
            </#if>
          </#list>
        </#list>
        <tr>
          <td > <span class="glyphicon glyphicon-info-sign"></span></td>
          <td class="chartExplanation"> <@m'vAxisTitle'/>: <@m'chartExplanation'/></td>
        </tr>
      </table>
    </div>

  </div>

  </#if>

<script>
  google.load("visualization", "1", {packages:["corechart"],language:'et'});
  google.setOnLoadCallback(drawChart);
  function drawChart() {
    var jsonData = $.ajax({
      url: "/admin/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {goalId: $("input").val()}
    }).responseText;
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));
    var data = google.visualization.arrayToDataTable(data1);
    var options = {
      seriesType: "bars",
      hAxis: {format: '####', title: "<@m'year'/>"},
      vAxes: {0:{format:'#%',  title: "<@m'vAxisTitle'/>", minValue: 0.0, viewWindow: {  min: 0.0  }, gridlines:{count:11}},
        1: {format: "#", title: "<@m'chartBudget'/>" , minValue:0.0, viewWindow: {  min: 0.0  },gridlines:{count:11}}},
      legend: { position: 'none'},
      colors: ['#1abc9c', '#3498db', '#9b59b6','#34495e', '#f1c40f','#e67e22', '#e74c3c','#95a5a6', '#d35400', '#2980b9', '#16a085'],
      interpolateNulls:true
    };
    seriesOption = {};
    seriesOption[ data.getNumberOfColumns()-2] = {targetAxisIndex:1, pointSize:5, type: "line"};
    for(i=1; i<data.getNumberOfColumns()-2; i++) {
      seriesOption[i] = {targetAxisIndex: 0};
    }
    options.series = seriesOption;

    var formatter1 = new google.visualization.NumberFormat({pattern:'###%'});
    for(i=1; i<data.getNumberOfColumns()-1; i++) {
      formatter1.format(data, i);
    }

    var formatter2 = new google.visualization.NumberFormat({pattern:'###€'});
    formatter2.format(data,data.getNumberOfColumns()-1);

    var chart =  new google.visualization.ComboChart(document.getElementById('chart'));
    chart.draw(data, options);
  }
</script>
</@html>
