package dev.prateek.com.inventorys2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popey.inventory_app.data.ProductContract;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageButton saleImageButton = view.findViewById(R.id.button_sale);
        final long id = cursor.getLong(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry._ID));
        final String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        final String productPrice = cursor.getString(cursor.getColumnIndexOrThrow("price"));
        final String productQuantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(productQuantity);

        //Declarate ImageButton
        saleImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(productQuantity);
                if (quantity > 0) {
                    quantity = quantity - 1;
                } else {
                    Toast.makeText(context, R.string.run_out, Toast.LENGTH_LONG).show();
                }
                String changedQuantity = String.valueOf(quantity);
                ContentValues values = new ContentValues();
                values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME, productName);
                values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
                values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, changedQuantity);
                context.getContentResolver().update(ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, id), values, null, null);
            }
        });

    }

}
