package com.tobeto.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GlobalExceptionDTO> error(Exception ex) {
		log.error(ex);
		GlobalExceptionDTO dto = new GlobalExceptionDTO();
		dto.setCode(1001);
		dto.setMessage("An Error Occurred");
		return ResponseEntity.internalServerError().body(dto);
	}
}
//bu sayede tum hatalar icin bu metodum calissin istiyorum
//geriye responseentity dondurecek ama burda string yerine class dondurursek daha iyi json hata ihtimalinden dolayi
//ondan bir tane dto classi yarattik
//eger consola Sout degil kendi log formatinda yazmak istiyorsak o zaman buraya
//en basa @log4j2 ekledik bu otomatik olarak classin icine bir degisken yaratiyor
//bunun uzerinden log. Diyerek exceptionu direkt olarak log.error a verdik
//ekran ciktisini otomatik olarak diger codelarin ciktilariyla ayni cikmasini sagliyor
//bir dto olusturduk ve icini doldurduk ne cevap donecek diye hata durumunda
//degiskene aktarip arada mudahale etme sansini elde etmis oldum
//her zaman bu hata mesaji donuyor spesifik hatalr icin bunlari degistirebiliriz
