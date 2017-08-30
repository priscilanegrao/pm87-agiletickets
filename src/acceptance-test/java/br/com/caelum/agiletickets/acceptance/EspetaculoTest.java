package br.com.caelum.agiletickets.acceptance;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementValue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class EspetaculoTest {
	
	@Test
	public void testeCadastroEspetaculo(){
		FirefoxDriver driver = new FirefoxDriver();
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		driver.get("http://localhost:8080/espetaculos");
		WebElement form = driver.findElement(By.id("addForm"));
		form.findElement(By.name("espetaculo.nome")).sendKeys("Rei Leão");
		form.findElement(By.name("espetaculo.descricao")).sendKeys("É um musical sobre um leão que vira Rei.");
		form.findElement(By.name("espetaculo.tipo")).sendKeys("Teatro");
		form.findElement(By.name("espetaculo.estabelecimento.id")).sendKeys("Casa de shows");
		
		form.submit();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(not(textToBePresentInElementValue(By.name("espetaculo.nome"), "Rei Leão")));
		
		WebElement linha = driver.findElement(By.cssSelector("table tbody tr:last-child"));
		List<WebElement> colunas = linha.findElements(By.cssSelector("td"));
		
		Assert.assertEquals("Rei Leão", colunas.get(1).getText());
		Assert.assertEquals("É um musical sobre um leão que vira Rei.", colunas.get(2).getText());
		Assert.assertEquals("TEATRO", colunas.get(3).getText());
		
		driver.close();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
		EntityManager manager = emf.createEntityManager();
		manager.getTransaction().begin();
		manager.createNativeQuery("delete from Espetaculo where nome = 'Rei Leão'").executeUpdate();
		manager.getTransaction().commit();
	}
	
	@Test
	public void testeCadastroEspetaculoEmBranco(){
		FirefoxDriver driver = new FirefoxDriver();
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		driver.get("http://localhost:8080/espetaculos");
		WebElement form = driver.findElement(By.id("addForm"));
		
		form.findElement(By.name("espetaculo.tipo")).sendKeys("Teatro");
		form.findElement(By.name("espetaculo.estabelecimento.id")).sendKeys("Casa de shows");
		
		form.submit();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("errors")));
		
		WebElement ul = driver.findElement(By.id("errors"));
		List<WebElement> lis = ul.findElements(By.cssSelector("li"));
		
		Assert.assertEquals("Nome do espetáculo não pode estar em branco", lis.get(0).getText());
		Assert.assertEquals("Descrição do espetáculo não pode estar em branco", lis.get(1).getText());
		
		driver.close();
		
	}
	
	
}