<@html>
  <#if goals?has_content>
    <#list goals as goal>
    <div class="panel panel-default">
      <div class="goal">
        <div class="panel-heading">
          <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>Translation missing</i></#if></#if></h4>
          <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>Translation missing</i></#if></#if></div>
          <h4 class="budget"><@m'budget'/> ${goal.budget?c} €</h4>
        </div>
      </div>
      <div class="panel-body"id="chart"></div>
    </div>
    </#list>
  </#if>

<script>
  google.load("visualization", "1", {packages:["corechart"]});
  google.setOnLoadCallback(drawChart);
  function drawChart() {
    var data = google.visualization.arrayToDataTable([
      ['Year', 'Sinine', 'Punane'],
      ['2004',  1000,      400],
      ['2005',  1170,      460],
      ['2006',  660,       1120],
      ['2007',  1030,      540]
    ]);

    var options = {
      title: 'Graafikud',
      curveType: 'function',
      legend: { position: 'bottom' }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart'));

    chart.draw(data, options);
  }
</script>
</@html>
