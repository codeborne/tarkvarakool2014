<@html>
  <#if metric?has_content>
  <input type="hidden" value="${metric.id?c}" name="metricId">
    <#if loggedInUsername??>
    <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
    </#if>
  <div class="panel panel-default panel-chart">
    <div class="goal">
      <div class="panel-heading panel-heading-chart">
        <h4 class="name printMetricName line-break"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></h4>
      </div>
    </div>
    <div class="panel-body" id="chart">
    </div>
  </div>

  </#if>

<script>
  google.load("visualization", "1.1", {packages:["corechart"],language:'et'});
  google.setOnLoadCallback(drawChart);
  var csrfToken = $("[name=csrfToken]").val();
  function drawChart() {
    var jsonData = $.ajax({
      url: "/metrics/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {metricId: $("input").val(), csrfToken: csrfToken, isMeasuredData: true}
    }).responseText;

    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));

    var oldData = google.visualization.arrayToDataTable(data1.pop());
    var newData = google.visualization.arrayToDataTable(data1.pop());

    var options = {
      enableInteractivity: false,
      hAxis: {title: "<@m'year'/>"},
      vAxis: {title: oldData.getColumnLabel(0) , minValue: 0.0, viewWindow: {  min: 0.0  }},
      legend: { position: 'none'},
//      colors:['#FF0000'],
      diff: { newData: { widthFactor: 0.6 }, oldData:{color:'#999999'} }
    };



    var chart = new google.visualization.ColumnChart(document.getElementById('chart'));
    var diffData = chart.computeDiff(oldData, newData);
    chart.draw(diffData, options);
console.log(diffData);
  }
</script>
</@html>
