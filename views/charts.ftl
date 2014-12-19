<@html>
  <#if goal?has_content>
  <input type="hidden" value="${goal.id?c}" name="goalId">
  <#if loggedInUsername??>
  <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
  </#if>
    <div class="panel panel-default panel-chart">
      <div class="goal">
        <div class="panel-heading panel-heading-chart">
          <a href="/home#${goal.id}">
            <button class="values-view-button btn btn-default btn-sm">
              <span class="glyphicon glyphicon-home" title="<@m'goToGoal'/>"></span>
              <span class="sr-only">"<@m'goToGoal'/>"</span>
            </button>
          </a>
          <h4 class="name line-break"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
          <div class="line-break"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
          <h4 class="budget"><@m'budget'/> ${goal.budget} €</h4>
        </div>
      </div>
      <div class="panel-body" id="chart">
      </div>
      <div class="chartLegend" style="background-color: white; padding-left: 20px;">
        <table class="legendTable">
          <#list graphColors as color>
            <#list metricsWithValidLevels as metric>
              <#if metric_index == color_index>
                <tr>
                  <td><div class="legendRow" id="legendBox_${color_index}" style="background-color: ${color};"></div></td>
                  <td><span class="legendMetricName"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></span></td>
                </tr>

              </#if>
              <#if !metric_has_next && ((metric_index+1) == color_index)>
                <tr>
                  <td><div class="legendRow" id="legendBox_${color_index}" style="background-color: ${color};"></div></td>
                  <td><span class="legendMetricName"><@m'budgetLegend'/></span></td>
                </tr>
              </#if>
            </#list>

            <#if  metricsWithValidLevels?size == 0 && color_index ==0>
              <tr>
                <td><div class="legendRow" id="legendBox_${color_index}" style="background-color: ${color};"></div></td>
                <td><span class="legendMetricName"><@m'budgetLegend'/></span></td>
              </tr>
            </#if>
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
  var csrfToken = $("[name=csrfToken]").val();
  function drawChart() {
    var jsonData = $.ajax({
      url: "/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {goalId: $("input").val(), csrfToken: csrfToken}
    }).responseText;
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));
    var data = google.visualization.arrayToDataTable(data1);
    console.log(data1);
    vAxisMinValue = 0;
    vAxisMaxValue = 1.2;
    hAxisMinViewValues = data.getValue(0,0)+0.5;
    hAxisMaxViewValues = data.getValue(data.getNumberOfRows()-1,0)-0.5;
    gridLinesCount = data.getNumberOfRows()-2;

    var options = {
      seriesType: "bars",
      hAxis: {format: '####', viewWindow: {  min: hAxisMinViewValues, max: hAxisMaxViewValues  }, title: "<@m'year'/>", gridlines:{count:gridLinesCount, color:"#ffffff"}},
      vAxis: {format:'#%',  title: "<@m'vAxisTitle'/> / <@m'chartBudget'/>", minValue: vAxisMinValue, maxValue:vAxisMaxValue, viewWindow: {  min:vAxisMinValue , max:vAxisMaxValue }, gridlines:{count:7}},
      legend: { position: 'none'},
      colors: ['#1abc9c', '#3498db', '#9b59b6','#34495e', '#f1c40f','#e67e22', '#e74c3c','#95a5a6', '#d35400', '#2980b9', '#16a085'],
      interpolateNulls:true
    };
    seriesOption = {};

    seriesOption[ data.getNumberOfColumns()-3] = {pointSize:5, type: "line"};
    seriesOption[ data.getNumberOfColumns()-2] = {type: "line", color: "black", lineWidth:"1",  enableInteractivity: false};
    options.series = seriesOption;

    var formatter1 = new google.visualization.NumberFormat({pattern:'###%'});
    for(i=1; i<data.getNumberOfColumns(); i++) {
      formatter1.format(data, i);
    }

    var chart =  new google.visualization.ComboChart(document.getElementById('chart'));
    chart.draw(data, options);
  }
</script>
</@html>
