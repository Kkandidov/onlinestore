$(function() {

    // solving the active menu problem
    switch (menu) {
        case 'About Us':
            $('#about').addClass('active');
            break;
        case 'Contact Us':
            $('#contact').addClass('active');
            break;
        case 'All Products':
            $('#listProducts').addClass('active');
            break;
        default:
            if (menu == "Home") break;
            $('#listProducts').addClass('active');
            $('#a_' + menu).addClass('active');
            break;
    }

	var jsonUrl = window.contextRoot + '/rest/json/data/all/products';

    // code for jquery dataTable
    var $table = $('#productListTable');
	        if($table.length){
			$table.DataTable({
			lengthMenu: [[3,5,10,-1],['3 Records','5 Records','10 Records', 'ALL']],
		    pageLength: 5,
			ajax:{
				url: jsonUrl,
				dataSrc: ''
			},
			columns:[
					{
						data: null,
						render: function(data, type, row){
							 return '<img src="' + window.contextRoot + '/assets/images/products/' + data.id + '/'
							 + data.code + '.jpeg" class="dataTableImg"/>';
						}
					},
					{
						data: 'name'
					},
					{
						data: 'brand'
					},
					{
						data: 'unitPrice',
						mRender: function(data, type, row){
							 return '$ ' + data;
						}
					},
					{
						data: 'quantity'
					},
					{
						data: 'id'
					}
					]
			});
    	}
});