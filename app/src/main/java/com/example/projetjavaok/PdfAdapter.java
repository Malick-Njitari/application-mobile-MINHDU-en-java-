package com.example.projetjavaok;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.example.projetjavaok.model.PdfItem;

import java.util.List;
//Ici c'est un dossier qui consiste

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    private List<String> pdfList;

    public PdfAdapter(List<String> pdfList) {
        this.pdfList = pdfList;
    }
//j'ai touché le code ci  toute la page
    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pdf, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        String pdfName = pdfList.get(position);
        // Ici vous pouvez mettre le pdfName dans un TextView ou autre
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    class PdfViewHolder extends RecyclerView.ViewHolder {
        // Initialisez vos vues ici

        public PdfViewHolder(View itemView) {
            super(itemView);
            // Initialisations
        }
    }

}

//public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
//    private List<Uri> pdfUris;
//
//    public PdfAdapter(List<Uri> pdfUris) {
//        this.pdfUris = pdfUris;
//    }
//
//    @Override
//    public PdfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_pdf, parent, false);
////        .inflate(R.layout.pdf_item_layout, parent, false);
//        return new PdfViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(PdfViewHolder holder, int position) {
//        Uri pdfUri = pdfUris.get(position);
//        holder.bind(pdfUri);
//    }
//
//    @Override
//    public int getItemCount() {
//        return pdfUris.size();
//    }
//
//    public class PdfViewHolder extends RecyclerView.ViewHolder {
//        public PdfViewHolder(View itemView) {
//            super(itemView);
//            // Initialisez vos vues ici
//        }
//
//        public void bind(Uri pdfUri) {
//            // Mettez à jour votre vue avec l'URL du PDF et ajoutez un écouteur pour le téléchargement
//
//            itemView.setOnClickListener(v -> {
//                Context context = itemView.getContext();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(pdfUri, "application/pdf");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                context.startActivity(intent);
//            });
//        }
//    }
//}

//public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder>{
//
//    private List<String> pdfList;
//
//    public PdfAdapter(List<String> pdfList) {
//        this.pdfList = pdfList;
//    }
//
//    @NonNull
//    @Override
//    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
//        return new PdfViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
//        holder.textViewPdfName.setText(pdfList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return pdfList.size();
//    }
//
//    static class PdfViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewPdfName;
//        PdfViewHolder(View itemView) {
//            super(itemView);
//            textViewPdfName = itemView.findViewById(R.id.textViewPdfName);
//        }
//    }

//}
