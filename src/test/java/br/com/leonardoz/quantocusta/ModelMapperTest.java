package br.com.leonardoz.quantocusta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import br.com.leonardoz.quantocusta.contrato.OrcamentoDto;
import br.com.leonardoz.quantocusta.contrato.PagamentoDto;
import br.com.leonardoz.quantocusta.entidade.Orcamento;
import br.com.leonardoz.quantocusta.entidade.Pagamento;

public class ModelMapperTest {

	@Test
	public void t() {
		Orcamento orcamento = new Orcamento("1", "22", LocalDateTime.now(), LocalDateTime.now(), "Eu");
		ModelMapper m = new ModelMapper();
		m.addConverter(new Converter<LocalDate, String>() {
			public String convert(MappingContext<LocalDate, String> context) {
				return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(context.getSource());
			}
		});
		m.addMappings(new PropertyMap<Pagamento, PagamentoDto>() {

			@Override
			protected void configure() {
				map().setOrcamentoId(source.getOrcamento().getId().intValue());
			}
		});
		OrcamentoDto map = m.map(orcamento, OrcamentoDto.class);
		System.out.println(map.toString());
		Pagamento pagamento = new Pagamento(10, 1.4, 0, 0, orcamento);
		PagamentoDto map2 = m.map(pagamento, PagamentoDto.class);
		System.out.println(map2);
	}

}
