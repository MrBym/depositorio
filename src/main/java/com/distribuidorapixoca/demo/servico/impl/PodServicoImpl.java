package com.distribuidorapixoca.demo.servico.impl;

import com.distribuidorapixoca.demo.builder.PodBuilder;
import com.distribuidorapixoca.demo.dto.PodDTO;
import com.distribuidorapixoca.demo.model.Pod;
import com.distribuidorapixoca.demo.repositorio.PodRepositorio;
import com.distribuidorapixoca.demo.servico.PodServico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PodServicoImpl implements PodServico {


    public Pod pod;
    private PodRepositorio podRepositorio;

    public PodServicoImpl(PodRepositorio podRepositorio) {
        this.podRepositorio = podRepositorio;
    }

    public List<Pod> getAllPod() {
        List<Pod> pods = podRepositorio.findAll();
        return pods;
    }

    public PodDTO createPod(PodDTO pdt) {

        Pod podForSave = findPodByNomeAndSabor(pdt);
        if(podForSave != null) {
            podForSave.setQuantidade(podForSave.getQuantidade() + pdt.getQuantidade());
        } else {
            podForSave = PodBuilder.builder()
                    .withNome(pdt.getNome()).withPreco(pdt.getPreco())
                    .withSabor(pdt.getSabor()).withQuantidade(pdt.getQuantidade())
                    .withMarca(pdt.getMarca()).withPuffs(pdt.getPuffs())
                    .withIsDescatavel(pdt.getIsDescartavel())
                    .build();
        }

        podRepositorio.save(podForSave);
        return pdt;
    }

    public Pod findPodByNomeAndSabor(PodDTO podDTO){
        return podRepositorio.findByNomeAndSabor(podDTO.getNome(), podDTO.getSabor());
    }

    public void deletepod(Long id){
        podRepositorio.deleteById(id);
    }

    public Pod updatePodQuant(Long quantidade, Long id){
        Optional<Pod> pod = podRepositorio.findById(id);
        Pod podNovo = new Pod();
        if(!pod.isEmpty()){
            podNovo.setQuantidade(quantidade);
            podNovo.setId(id);
            podNovo.setMarca(pod.get().getMarca());
            podNovo.setPreco(pod.get().getPreco());
            podNovo.setPuffs(pod.get().getPuffs());
            podNovo.setNome(pod.get().getNome());
            podNovo.setSabor(pod.get().getSabor());
            podNovo.setDescartavel(pod.get().isDescartavel());
            podRepositorio.save(podNovo);
        }
        return podNovo;
    }

}
