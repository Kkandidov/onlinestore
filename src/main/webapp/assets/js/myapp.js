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

    // code for jquery dataTable
    var $table = $('#productListTable');
	if($table.length){
	    var jsonUrl = '';
        if (window.categoryId == '') {
            jsonUrl = window.contextRoot + '/rest/json/data/all/products';
        } else {
            jsonUrl = window.contextRoot + '/rest/json/data/category/'
            + window.categoryId + '/products';
        }
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
						data: 'id',
						bSortable: false,
						render: function(data, type, row){
							var str = '';
							str += '<a href="' + window.contextRoot + '?command=show.product&id=' + data + '" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open" aria-label="Left Align"></span></a>';
							str += '<a href="' + window.contextRoot + '/cart/add/' + data + '/product" class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart" aria-label="Left Align"></span></a>';
							return str;
						}
					}
					]
			});
    	}
});