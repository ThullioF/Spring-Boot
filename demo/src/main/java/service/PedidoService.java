package service;

import dto.ItemPedidoDTO;
import dto.PedidoDTO;
import entity.ItemPedido;
import entity.Pedido;
import entity.Produto;
import entity.Usuario;
import repository.PedidoRepository;
import repository.ProdutoRepository;
import repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        
        // Busca o usuário
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getIdUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        pedido.setUsuario(usuario);
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setData(pedidoDTO.getData());
        // Constrói os itens do pedido
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getIdProduto())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQtde(itemDTO.getQtde());
            itemPedido.setPreco(itemDTO.getPreco());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }
        
        return pedidoRepository.save(pedido);
    }

    public void excluir(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
    }
}