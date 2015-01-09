//draw charts in pages
function drawChart(div, value) {
    if(0 <= value && value <= 1) {
        var data = [
                    {
                        'color'       : 'rgb(244,84,134)',
                        'value'       : value
                    },
                    {
                        'color'       : 'rgb(54,54,54)',
                        'value'       : 1 - value
                    }
        ];
        var label = value * 100 + '%';
        drawPieChart(div, data, label);
    }
    
}

function drawPieChart(elementId, data, label) {
    var containerEl = document.getElementById(elementId),
        width = containerEl.clientWidth,
        height = containerEl.clientHeight,
        radius = Math.min(width, height) / 2,
        container = d3.select(containerEl),
        svg = container.append('svg')
                       .attr('width', width).attr('height', height);
    var pie = svg.append('g').attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');
    

    var pieData = d3.layout.pie().sort(null)
                    .value(function(d) {return d.value;});

    var arc = d3.svg.arc()
                .outerRadius(radius - 5)
                .innerRadius((radius - 5) * 0.80);

    pie.datum(data)
        .selectAll('path')
        .data(pieData)
        .enter()
        .append('path')
        .attr('fill', function(d) {
            return d.data.color;
        })
        .attr("stroke", "#000000")
        .attr("stroke-width", 0.5)
        .attr('d', arc);
    
    svg.select('g').append('circle').attr('r', 30)
       .attr('fill', 'rgb(54,54,54)');
    svg.select('g').append('text').attr('text-anchor', 'middle')
       .attr('fill', '#ffffff').attr('y', 8)
       .text(label).attr('class', 'h3');
}