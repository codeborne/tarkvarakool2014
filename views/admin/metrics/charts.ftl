<@html>
  <#if metric?has_content>
  <input type="hidden" value="${metric.id?c}" name="metricId">
  <div class="panel panel-default">
    <div class="goal">
      <div class="panel-heading">
        <h4 class="name"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if></#if></h4>
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
      url: "/admin/metrics/chart",
      type: "POST",
      dataType:"json",
      async: false,
      data: {metricId: $("input").val()}
    }).responseText;
    console.log(jsonData);
    var data1 = JSON.parse(jsonData.replace(/&quot;/g, '"'));

    console.log(data1);
    var data = google.visualization.arrayToDataTable(data1);

    var options = {
      hAxis: {title: "<@m'year'/>"},
      vAxis: {title: "<@m'vAxisTitle'/>"},
      legend: { position: 'none'},
//      colors: ['#1abc9c', '#3498db', '#9b59b6','#34495e', '#f1c40f','#e67e22', '#e74c3c','#95a5a6', '#d35400', '#2980b9', '#16a085'],
    };

//    var formatter1 = new google.visualization.NumberFormat({pattern:'###%'});
//
//      formatter1.format(data);



    var chart =  new google.visualization.ColumnChart(document.getElementById('chart'));


    chart.draw(data, options);
  }
</script>
</@html>
