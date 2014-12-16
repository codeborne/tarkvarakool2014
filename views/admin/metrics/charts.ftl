<@html>
  <#if metric?has_content>
    <input type="hidden" value="${metric.id?c}" name="metricId">
    <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
    <div class="panel panel-default panel-chart">
      <div class="goal">
        <div class="panel-heading panel-heading-chart">
          <h4 class="name printMetricName line-break"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></h4>
        <div class="line-break"><#if language == 'et'>${metric.publicDescription!""}<#elseif language == 'en'><#if metric.engPublicDescription??>${metric.engPublicDescription}<#else><i>${metric.publicDescription!""}</i></#if></#if></div>
        </div>
      </div>
    <div class="panel-body" id="chart"></div>
    <div class="chartLegend" style="background-color: white; padding-left: 20px;">
      <table class="legendTable">
              <tr>
                <td><div class="legendRow" id="legendBox" style="background-color: #cccccc;"></div></td>
                <td><span class="legendMetricName"><@m'forecast'/></span></td>
              </tr>
              <tr>
                <td><div class="legendRow" id="legendBox" style="background-color:#3498db;"></div></td>
                <td><span class="legendMetricName"><@m'measuredValue'/></span></td>
              </tr>
      </table>
    </div>
  </div>

  </#if>

<script>
  google.load("visualization", "1.1", {packages:["corechart"],language:'et'});
  google.setOnLoadCallback(drawChart);
  var csrfToken = $("[name=csrfToken]").val();
  function drawChart() {
    var jsonData = $.ajax({
      url: "/admin/metrics/chart",
      type: "POST",
      dataType: "json",
      async: false,
      data: {metricId: $("input").val(), csrfToken: csrfToken}
    }).responseText;
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));
    var oldData = google.visualization.arrayToDataTable(data1.pop());
    var newData = google.visualization.arrayToDataTable(data1.pop());

    var options = {
      hAxis: {title: "<@m'year'/>", slantedText:true},
      vAxis: {title: oldData.getColumnLabel(0) , minValue: 0.0, viewWindow: {  min: 0.0  }},
      legend: { position: 'none'},
      colors:['#3498db'],
      diff: { newData: { widthFactor: 0.6 }, oldData:{color:'#cccccc'} }
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('chart'));
    var diffData = chart.computeDiff(oldData, newData);
    diffData.setColumnProperty(4, "role", 'tooltip');
    diffData.removeColumn(3);
    chart.draw(diffData, options);
  }
</script>
</@html>
