package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {

	@Test
	public void podeReservarQuantidadeMenorDeIngressos() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void naoPodeReservarQuantidadeMaiorDeIngressos() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		Assert.assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		Assert.assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
	
	@Test
	public void podeReservarQuantidadeEquivalenteIngressos() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);
		
		boolean podeReservar = sessao.podeReservar(5);
		Assert.assertEquals(true, podeReservar);
	
	}
	
}
