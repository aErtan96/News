package com.dertsizvebugsiz.news.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.Currency;
import com.dertsizvebugsiz.news.utils.Formatter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder> {

    public Currency[] currencies;
    public MainActivity mainActivity;
    private LayoutInflater layoutInflater;

    private static NumberFormat formatter_decimal_7 = new DecimalFormat("#0.0000000");
    private static NumberFormat formatter_decimal_2 = new DecimalFormat("#0.00");

    public CurrenciesAdapter(Currency[] currencies, MainActivity mainActivity){
        this.mainActivity = mainActivity;
        layoutInflater = mainActivity.getLayoutInflater();
        this.currencies = currencies;
    }

    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.currencies_recycler_item, parent,false);
        return new CurrencyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        holder.setData(currencies[position]);
    }

    @Override
    public int getItemCount() {
        return currencies.length;
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder{

        TextView symbolTv, priceChangeTv, priceTv, nameTv;
        CardView cardView;

        public CurrencyViewHolder(View itemView) {
            super(itemView);
            symbolTv = itemView.findViewById(R.id.currencies_item_symbol);
            priceChangeTv = itemView.findViewById(R.id.currencies_item_price_change);
            priceTv = itemView.findViewById(R.id.currencies_item_price);
            nameTv = itemView.findViewById(R.id.currencies_item_name);
            cardView = itemView.findViewById(R.id.currencies_item_card);
        }

        public void setData(Currency currency){
            nameTv.setText(currency.name);
            /*
            if(currency.price < 1){
                priceTv.setText(("$" + formatter_decimal_7.format(currency.price)).replace('.',','));
            }
            else{
                priceTv.setText(("$" + formatSignificant(currency.price, 8)).replace('.',','));
            }
            */
            priceTv.setText(Formatter.formatCurrencyValue(currency.price));
            symbolTv.setText(currency.symbol);

            String priceChangeStr = formatter_decimal_2.format(currency.dailyChangePercent) + "%";
            if(currency.dailyChangePercent > 0){
                priceChangeStr = "▲" + priceChangeStr;
                priceChangeTv.setTextColor(mainActivity.getResources().getColor(R.color.priceChangePositive));
                cardView.getBackground().setColorFilter(Color.parseColor("#F5FBF4"), PorterDuff.Mode.SRC_ATOP);
            }
            else{
                priceChangeStr = "▼" + priceChangeStr.replace("-", "");
                priceChangeTv.setTextColor(mainActivity.getResources().getColor(R.color.priceChangeNegative));
                cardView.getBackground().setColorFilter(Color.parseColor("#FBF4F4"), PorterDuff.Mode.SRC_ATOP);
            }
            priceChangeTv.setText(priceChangeStr);
        }

        public String formatSignificant(double value, int significant)
        {
            MathContext mathContext = new MathContext(significant, RoundingMode.DOWN);
            BigDecimal bigDecimal = new BigDecimal(value, mathContext);
            return bigDecimal.toPlainString();
        }
    }

}
