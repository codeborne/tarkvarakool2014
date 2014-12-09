<@html>
  <#if metric?has_content>
  <input type="hidden" value="${metric.id?c}" name="metricId">
  <div class="panel panel-default panel-chart">
    <div class="goal">
      <div class="panel-heading panel-heading-chart">
        <h4 class="name printMetricName"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></h4>
      </div>
    </div>
    <div class="panel-body" id="chart">
    </div>
  </div>

  </#if>

<script>
  google.load("visualization", "1", {packages:["corechart"],language:'et'});
  google.setOnLoadCallback(drawChart);
  function drawChart() {
    var jsonData = $.ajax({
      url: "/metrics/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {metricId: $("input").val()}
    }).responseText;
    console.log(jsonData);
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));
    var data = google.visualization.arrayToDataTable(data1);

    var options = {
      hAxis: {title: "<@m'year'/>"},
      vAxis: {title: data.getColumnLabel(0) , minValue: 0.0, viewWindow: {  min: 0.0  }},
      legend: { position: 'none'}
    };
    data.setColumnProperty(2, "role", 'tooltip');
    var chart =  new google.visualization.ColumnChart(document.getElementById('chart'));
    chart.draw(data, options);
  }
</script>
</@html>
